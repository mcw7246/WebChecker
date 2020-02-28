package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import static spark.Spark.halt;

public class PostSignInRoute implements Route{


    //
    // Static methods
    //
    static final String USERNAME_PARAM = "username";
    static final String MESSAGE_ATTR = "message";
    static final String MESSAGE_TYPE_ATTR = "messageType";

    static final String ERROR_TYPE = "error";
    static final String VIEW_NAME = "signin.ftl";
    static final String SIGN_IN_ERROR = "%s is already taken. Please try another username.";
    //
    // Attributes
    //
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    //
    // Constructor
    //
    public PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine)
    {
        // validation
        Objects.requireNonNull(playerLobby, "gameCenter must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    static String makeInvalidArgMessage(final String username)
    {
        return String.format("You entered %s; please create a valid username with only letters and numbers.", username);
    }

    @Override
    public String handle(Request request, Response response)
    {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();
        vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        vm.put(GetHomeRoute.NEW_PLAYER_ATTR, Boolean.FALSE);

        // retrieve the game object
        final Session session = request.session();
        final PlayerLobby playerLobby = session.attribute(GetHomeRoute.PLAYER_LOBBY_KEY);

        /* A null playerServices indicates a timed out session or an illegal request on this URL.
         * In either case, we will redirect back to home.
         */
        if(playerLobby != null)
        {
            final String username = request.queryParams(USERNAME_PARAM);
            ModelAndView mv;
            switch(playerLobby.isValidUsername(username))
            {
                case INVALID:
                    mv = error(vm, makeInvalidArgMessage(username));
                    break;
                case TAKEN:
                    vm.put(GetSignInRoute.SIGN_IN_ATTR, playerLobby);
                    mv = taken(vm, String.format(SIGN_IN_ERROR, username));
                    break;
                case AVAILABLE:
                    mv = available(vm, playerLobby);
                    break;

                default:
                    throw new NoSuchElementException("Invalid result of username recieved.");
            }
            //vm.put(GetHomeRoute.GAME_BEGINS_ATTR, playerLobby.isStartingGame);
            //TODO: Change the isStartingGame() to what is needed from PlayerLobby

            System.out.println("UsernameResult: " + playerLobby.getUsernameResult().toString());
            return templateEngine.render(mv);
        }
        else
            {
            response.redirect(WebServer.SIGNIN_URL);
            halt();
            return null;
        }
    }

    private ModelAndView error(final Map<String, Object> vm, final String message)
    {
        vm.put(MESSAGE_ATTR, message);
        vm.put(MESSAGE_TYPE_ATTR, ERROR_TYPE);
        return new ModelAndView(vm, VIEW_NAME);
    }
    private ModelAndView taken(final Map<String, Object> vm, final String message)
    {
        vm.put(MESSAGE_ATTR, message);
        vm.put(MESSAGE_TYPE_ATTR, ERROR_TYPE);
        return new ModelAndView(vm, VIEW_NAME);
    }
    private ModelAndView available(final Map<String, Object> vm, final PlayerLobby playerLobby)
    {
        vm.put(GetSignInRoute.SIGN_IN_ATTR, playerLobby.getUsername());
        return new ModelAndView(vm, GetSignInRoute.SIGN_IN_ATTR);
    }
}