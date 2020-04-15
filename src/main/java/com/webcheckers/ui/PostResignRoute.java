package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Player;
import javafx.geometry.Pos;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.GetHomeRoute.PLAYER_KEY;
import static com.webcheckers.ui.GetHomeRoute.VIEW_NAME;
import static com.webcheckers.ui.WebServer.HOME_URL;

public class PostResignRoute implements Route
{
    final Map<String, Object> modeOptions = new HashMap<>(2);

    Gson gson = new Gson();

    final TemplateEngine templateEngine;


    public PostResignRoute(TemplateEngine templateEngine)
    {
        this.templateEngine = templateEngine;
    }


    @Override
    public Object handle(Request request, Response response) throws Exception
    {
        final Session session = request.session();
        final Player player = session.attribute(PLAYER_KEY);

        final Map<String, Object> vm = new HashMap<>();

        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage",player.getUsername() + " has resigned.");

        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));



        response.redirect(WebServer.HOME_URL);



        //return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        return null;
    }

}
