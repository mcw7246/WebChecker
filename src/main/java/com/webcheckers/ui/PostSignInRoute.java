package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class PostSignInRoute {


    //
    // Static methods
    //

    //
    // Attributes
    //
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    //
    // Constructor
    //
    public PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(playerLobby, "gameCenter must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response) {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();
        vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        vm.put(GetHomeRoute.NEW_PLAYER_ATTR, Boolean.FALSE);

        // retrieve the game object
        final Session session = request.session();
        final PlayerLobby playerLobby = session.attribute(GetHomeRoute.PLAYERLOBBY_KEY);
        //TODO: create PLAYERLOBBY_KEY

        /* A null playerServices indicates a timed out session or an illegal request on this URL.
         * In either case, we will redirect back to home.
         */
        if(playerLobby != null) {
            vm.put(GetHomeRoute.GAME_BEGINS_ATTR, playerLobby.isStartingGame());
            //TODO: Change the isStartingGame() to what is needed from PlayerLobby

            // retrieve request parameter
            final String guessStr = request.queryParams(GUESS_PARAM);

            // convert the input
            try {
                guess = Integer.parseInt(guessStr);
            } catch (NumberFormatException e) {
                // re-display the guess form with an error message
                return templateEngine.render(error(vm, makeBadArgMessage(guessStr)));
            }

            // make the guess and create the appropriate ModelAndView for rendering
            ModelAndView mv;

            return templateEngine.render(mv);
        }
        else {
            response.redirect(WebServer.SIGNIN_URL);
            halt();
            return null;
        }
    }
}