package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mMainName;
    private TextView mOrigin;
    private TextView mAlsoKnownAs;
    private TextView mDescription;
    private TextView mIngredients;
    private LinearLayout llOrigin;
    private LinearLayout llAKA;
    private LinearLayout llDescription;
    private LinearLayout llIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mOrigin = findViewById(R.id.origin_tv);
        mAlsoKnownAs = findViewById(R.id.also_known_tv);
        mDescription = findViewById(R.id.description_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mMainName = findViewById(R.id.name_tv);
        llOrigin = findViewById(R.id.origin_ll);
        llAKA = findViewById(R.id.aka_ll);
        llDescription = findViewById(R.id.description_ll);
        llIngredients = findViewById(R.id.ingredients_ll);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mMainName.setText(sandwich.getMainName());

        if(sandwich.getPlaceOfOrigin() != null) {
            mOrigin.setText(sandwich.getPlaceOfOrigin());
        } else {
            hideOrigin();
        }

        if(sandwich.getAlsoKnownAs() != null) {
            for(String alsoKnown: sandwich.getAlsoKnownAs()) {
                if(mAlsoKnownAs.length() == 0) {
                    mAlsoKnownAs.append(alsoKnown);
                } else {
                    mAlsoKnownAs.append(", " + alsoKnown);
                }
            }
        } else {
            hideAKA();
        }

        if(sandwich.getDescription() != null) {
            mDescription.setText(sandwich.getDescription());
        } else {
            hideDescription();
        }

        if(sandwich.getIngredients() != null) {
            for(String ingredient: sandwich.getIngredients()) {
                if(mIngredients.length() == 0) {
                    mIngredients.append(ingredient);
                } else {
                    mIngredients.append("\n" + ingredient);
                }
            }
        } else {
            hideIngredients();
        }


    }

    private void hideOrigin() {
        llOrigin.setVisibility(View.INVISIBLE);
    }

    private void hideAKA() {
        llAKA.setVisibility(View.INVISIBLE);
    }

    private void hideDescription() {
        llDescription.setVisibility(View.INVISIBLE);
    }

    private void hideIngredients() {
        llIngredients.setVisibility(View.INVISIBLE);
    }
}
