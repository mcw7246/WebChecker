package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class PostResignRoute implements Route
{
    final Map<String, Object> modeOptions = new HashMap<>(2);

    @Override
    public Object handle(Request request, Response response) throws Exception
    {
        return null;
    }

/**
    modeOptions.put("isGameOver", true);
    modeOptions.put("gameOverMessage", );
    m.put("modeOptionsAsJSON", gson.toJson(modeOptions));
**/

}
