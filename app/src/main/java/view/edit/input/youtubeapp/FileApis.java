package view.edit.input.youtubeapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * * ============================================================================
 * * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * * Unauthorized copying of this file, via any medium is strictly prohibited
 * * Proprietary and confidential
 * * ----------------------------------------------------------------------------
 * * Created by: Mimo Saha on [28-Dec-2018 at 5:32 PM].
 * * Email: mimosaha@w3engineers.com
 * * ----------------------------------------------------------------------------
 * * Project: YouTubeVideoSearch.
 * * Code Responsibility: <Purpose of code>
 * * ----------------------------------------------------------------------------
 * * Edited by :
 * * --> <First Editor> on [28-Dec-2018 at 5:32 PM].
 * * --> <Second Editor> on [28-Dec-2018 at 5:32 PM].
 * * ----------------------------------------------------------------------------
 * * Reviewed by :
 * * --> <First Reviewer> on [28-Dec-2018 at 5:32 PM].
 * * --> <Second Reviewer> on [28-Dec-2018 at 5:32 PM].
 * * ============================================================================
 **/
public interface FileApis {

    //    https://www.googleapis.com/youtube/v3/videos?id=" + ids + "&key=" + KEY
//            + "&part=snippet,contentDetails,statistics,status"

    /*
    Get request to fetch city weather.Takes in two parameter-city name and API key.
    */
    @GET("/youtube/v3/videos")
    Call<PlaylistItemListResponse> getVideoInfo(@Query("id") String ids,
                                                @Query("key") String key,
                                                @Query("part") String part);

}
