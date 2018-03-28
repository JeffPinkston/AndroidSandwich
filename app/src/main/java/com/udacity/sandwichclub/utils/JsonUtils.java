package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        int i;
        try {
            JSONObject sandwichJSONObj = new JSONObject(json);
            if(sandwichJSONObj != null) {
                sandwich = new Sandwich();
                sandwich.setImage(sandwichJSONObj.getString("image"));

                JSONObject nameJSONObj = sandwichJSONObj.getJSONObject("name");
                sandwich.setMainName(nameJSONObj.getString("mainName"));

                JSONArray alsoKnowAsJSON = nameJSONObj.getJSONArray("alsoKnownAs");
                if(alsoKnowAsJSON.length() > 0) {
                    List<String> alsoKnownAsArr =  new ArrayList<String>();
                    for(i = 0; i < alsoKnowAsJSON.length(); i++) {
                        alsoKnownAsArr.add(alsoKnowAsJSON.getString(i));
                    }
                    sandwich.setAlsoKnownAs(alsoKnownAsArr);
                }
                if(sandwichJSONObj.getString("placeOfOrigin").length() > 0) {
                    sandwich.setPlaceOfOrigin(sandwichJSONObj.getString("placeOfOrigin"));
                }

                if(sandwichJSONObj.getString("description").length() > 0) {
                    sandwich.setDescription(sandwichJSONObj.getString("description"));
                }

                JSONArray ingredientsJSON = sandwichJSONObj.getJSONArray("ingredients");
                if(ingredientsJSON.length() > 0) {
                    List<String> ingredientsArr = new ArrayList<String>();
                    for(i = 0; i < ingredientsJSON.length(); i++) {
                        ingredientsArr.add(ingredientsJSON.getString(i));
                    }
                    sandwich.setIngredients(ingredientsArr);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
