package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import static spark.Spark.halt;

/**
UI controller to POST a sign in
@author: Mikayla Wishart 'mcw7246'
 **/

public class PostSignInRoute implements Route{


    //
    // Static methods
    //
    static final String USERNAME_PARAM = "username";
    static final String MESSAGE_ATTR = "message";

    static final String VIEW_NAME = "signin.ftl";
    static final String SIGN_IN_ERROR = "%s is already taken. Please try " +
            "another username.";
    //
    // Attributes
    //
    private Player player;
    private final TemplateEngine templateEngine;
    private PlayerLobby playerLobby;


    //
    // Constructor
    //
    public PostSignInRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
    {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    //creates the invalid error message
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

        // retrieve the game object
        final Session session = request.session();

        if(session.attribute(GetHomeRoute.PLAYER_KEY) == null)
        {
            final String username = request.queryParams(USERNAME_PARAM);
            ModelAndView mv;
            Player player = new Player(playerLobby);

            //goes through the cases and submits the correct message/response
            switch(player.isValidUsername(username))
            {
                case INVALID:
                    mv = error(vm, makeInvalidArgMessage(username));
                    break;
                case TAKEN:
                    mv = error(vm, String.format(SIGN_IN_ERROR, username));
                    break;
                case AVAILABLE:
                    session.attribute(GetHomeRoute.PLAYER_KEY, player);
                    available(response);
                default:
                    throw new NoSuchElementException("Invalid result of username received.");
            }

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
        vm.put(MESSAGE_ATTR, Message.error(message));
        return new ModelAndView(vm, VIEW_NAME);
    }

    private void available(Response response)
    {
        response.redirect(WebServer.HOME_URL);
        halt();
    }
}