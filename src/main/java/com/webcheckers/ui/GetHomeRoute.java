package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author Austin Miller 'akm8654@rit.edu'
 */
public class GetHomeRoute implements Route
{
    static final String TITLE_ATTR = "title";

    //private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
    static final String NEW_PLAYER_ATTR = "newPlayer";
    static final String VIEW_NAME = "home.ftl";
    static final String TITLE = "Welcome to WebCheckers! Please signin.";
    static final String PLAYER_KEY = "player";
    static final String PLAYER_LOBBY_KEY = "player-lobby";
    static final String SIGN_IN_KEY = "signIn";
    static final String PLAYER_NUM_KEY = "playerNum";
    static final String LIST_PLAYERS_KEY = "listPlayers";
    static final String CURRENT_USRER_ATTR = "currentUser";
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;


    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetHomeRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby)
    {
        this.lobby = playerLobby;
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response)
    {
        // retrieve the HTTP session
        final Session httpSession = request.session();

        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);

        // if this is a brand new browser session or a session that timed out
        if (httpSession.attribute(PLAYER_KEY) == null)
        {
            vm.put(SIGN_IN_KEY, false);
            vm.put(PLAYER_NUM_KEY, lobby.getUsernames().size());
            // get the object that will provide client-specific services for this player
            vm.put(NEW_PLAYER_ATTR, true);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
        else
        {

            vm.replace(SIGN_IN_KEY, true);
            vm.put("usernames", lobby.getUsernames());
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
    }
}
