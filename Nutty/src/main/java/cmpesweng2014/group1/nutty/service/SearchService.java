package cmpesweng2014.group1.nutty.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import cmpesweng2014.group1.nutty.dao.RecipeDao;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.RecipeRate;
import cmpesweng2014.group1.nutty.model.RecipeTag;

@Component
public class SearchService {

	@Autowired
	private RecipeDao recipeDao;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private RecommendationService recommService;
	
	public String[] getRelatedTerms(String searchString){
		
		if(searchString == null || searchString.trim().equals("")){
			return null;
		}
		
		List<String> searchTerms = Arrays.asList((searchString.toLowerCase()).split(" "));
		int termCount = searchTerms.size();
				
		String queryString = "";		
		for(int i=0; i<termCount; i++){
			queryString += searchTerms.get(i);
			if( i != termCount-1)
				queryString += ",";
		}
		
        RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject("http://conceptnet5.media.mit.edu/data/5.2/assoc/list/en/{queryTerms}?filter=/c/en&limit=10", String.class, queryString);

		List<String> relatedTerms = new ArrayList<String>();
		Matcher m = Pattern.compile("\\/c\\/en\\/(.*)").matcher(result);
		while (m.find()) {
			String term = m.group();
			if(!searchTerms.contains(term))
				relatedTerms.add(term);
		}
		
		if (relatedTerms.isEmpty()) {
			return null;
		}		
		return relatedTerms.toArray(new String[relatedTerms.size()]);
	}
	
	//descending order
	public Recipe[] sortByRate(Recipe[] recipes, String rateType){
		List<RecipeRate> recRates = new ArrayList<RecipeRate>();
		for(int i=0; i<recipes.length;i++){
			double rate;
			if(rateType.equals("overall")){
				rate = recipeService.avgOfAllRates(recipes[i].getRecipe_id());
			}
			else{
				rate = recipeDao.getAvgRateStatistic(rateType, recipes[i].getRecipe_id());
			}
			RecipeRate r=new RecipeRate();
			r.setRate(rate);
			r.setRecipe_id(recipes[i].getRecipe_id());
			recRates.add(r);
		}
		Collections.sort(recRates);
		Recipe[] rec=new Recipe[recipes.length];
		for(int i=0; i<recRates.size();i++){
			rec[i]= recipeDao.getRecipeById(recRates.get(i).getRecipe_id());
		}
		return rec;		
	}
	
	//takes the tags given by the user as a parameter, does semantic search and
	//returns sorted Recipe array according to the similarity.
	public Recipe[] semanticSearch(String tag){
		//original tags
		String tagz[] = tag.split(" ");
		int relatedTagsIndex = tagz.length;
		String[] related = getRelatedTerms(tag);
		int relatedTagsLength = related.length;
		
		String tags[] = new String[relatedTagsIndex + relatedTagsLength];
		System.arraycopy(tagz, 0, tags, 0, relatedTagsIndex);
		System.arraycopy(related, 0, tags, relatedTagsIndex, relatedTagsLength);
		
		Recipe[] foundRecipes = searchByAllTags(tags, relatedTagsIndex);
		return foundRecipes;
	}
	public Recipe[] searchByAllTags(String[] tags, int relatedTagsIndex){		
		List<RecipeTag> recTags = new ArrayList<RecipeTag>();
		
		//recipe id - value mapping
		//value will be increased by one for found original tags, by 0.5 for found related tags
		Map<Integer, Double> recValue = new HashMap<Integer, Double>();
		//deal with original tags
		for(int i=0; i<relatedTagsIndex;i++){
			Recipe[] foundRecipes=recipeDao.searchRecipesForATag(tags[i]);
			for(int k=0; k<foundRecipes.length;k++){
				int recipe_id=foundRecipes[k].getRecipe_id();
				Double value = recValue.get(recipe_id);
				//if this recipe was added before, increase the value
				if (value != null) {
					value=value+1;
					recValue.put(recipe_id, value);
				}
				//if this recipe wasn't added.
				else{
					recValue.put(recipe_id, 1.0);
				}
			}
		}
		//deal with related tags
		for(int i=relatedTagsIndex; i<tags.length;i++){
			Recipe[] foundRecipes2=recipeDao.searchRecipesForATag(tags[i]);
			if(foundRecipes2 != null){
				for(int k=0; k<foundRecipes2.length;k++){
					int recipe_id=foundRecipes2[k].getRecipe_id();
					Double value2 = recValue.get(recipe_id);
					//if this recipe was added before, increase the value
					if (value2 != null) {
						value2=value2+0.5;
						recValue.put(recipe_id, value2);
					}
					//if this recipe wasn't added.
					else{
						recValue.put(recipe_id, 0.5);
					}
				}
			}
		}
		//now store all the information in RecipeTag object, to do sorting according to
		//value/total number of tags ratio
		for(Integer key : recValue.keySet()) {
            Double value = recValue.get(key);
            RecipeTag recTag=new RecipeTag();
            recTag.setRecipe_id(key);
            recTag.setValueOfFound(value);
            recTag.setNumberOfTags(recipeDao.getNumberOfTags(key));
            recTag.setRatio(value/recipeDao.getNumberOfTags(key));
            recTags.add(recTag);
        }
		Collections.sort(recTags);
		Recipe[] rec=new Recipe[recTags.size()];
		for(int i=0; i<recTags.size();i++){
			rec[i]= recipeDao.getRecipeById(recTags.get(i).getRecipe_id());
		}
		return rec;		
	}
	
	
	
}
