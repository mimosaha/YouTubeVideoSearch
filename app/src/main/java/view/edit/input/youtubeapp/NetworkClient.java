package view.edit.input.youtubeapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * * ============================================================================
 * * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * * Unauthorized copying of this file, via any medium is strictly prohibited
 * * Proprietary and confidential
 * * ----------------------------------------------------------------------------
 * * Created by: Mimo Saha on [28-Dec-2018 at 5:29 PM].
 * * Email: mimosaha@w3engineers.com
 * * ----------------------------------------------------------------------------
 * * Project: YouTubeVideoSearch.
 * * Code Responsibility: <Purpose of code>
 * * ----------------------------------------------------------------------------
 * * Edited by :
 * * --> <First Editor> on [28-Dec-2018 at 5:29 PM].
 * * --> <Second Editor> on [28-Dec-2018 at 5:29 PM].
 * * ----------------------------------------------------------------------------
 * * Reviewed by :
 * * --> <First Reviewer> on [28-Dec-2018 at 5:29 PM].
 * * --> <Second Reviewer> on [28-Dec-2018 at 5:29 PM].
 * * ============================================================================
 **/
public class NetworkClient {

//    https://www.googleapis.com/youtube/v3/videos?id=" + ids + "&key=" + KEY
//            + "&part=snippet,contentDetails,statistics,status"

    public static final String BASE_URL = "https://www.googleapis.com";
    public static Retrofit retrofit;
    /*
    This public static method will return Retrofit client
    anywhere in the appplication
    */
    public static Retrofit getRetrofitClient() {
        //If condition to ensure we don't create multiple retrofit instances in a single application
        if (retrofit == null) {
            //Defining the Retrofit using Builder
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) //This is the only mandatory call on Builder object.
                    .addConverterFactory(GsonConverterFactory.create()) // Convertor library used to convert response into POJO
                    .build();
        }
        return retrofit;
    }

}
