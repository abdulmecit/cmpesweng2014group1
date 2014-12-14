package cmpesweng2014.group1.nutty.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import cmpesweng2014.group1.nutty.dao.RecipeDao;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.RecipeRate;

@Component
public class SearchService {

	@Autowired
	private RecipeDao recipeDao;
	
	@Autowired
	private RecipeService recipeService;
	
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
}
