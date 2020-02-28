package com.webcheckers.ui;

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

    private final TemplateEngine templateEngine;

    //
    // Constructor
    //
    PostSignInRoute(TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response) {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();
        vm.put(GetHomeRoute.TITLE_ATTR, GetGameRoute.TITLE);
        vm.put(GetHomeRoute.NEW_PLAYER_ATTR, Boolean.FALSE);

        // retrieve the game object
        final Session session = request.session();
        final PlayerServices playerServices = session.attribute(GetHomeRoute.PLAYERSERVICES_KEY);

        /* A null playerServices indicates a timed out session or an illegal request on this URL.
         * In either case, we will redirect back to home.
         */
        if(playerServices != null) {
            vm.put(GetGameRoute.GAME_BEGINS_ATTR, playerServices.isStartingGame());
            vm.put(GetGameRoute.GUESSES_LEFT_ATTR, playerServices.guessesLeft());

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