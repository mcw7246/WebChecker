package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.Player;
import org.w3c.dom.css.ViewCSS;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;

import com.webcheckers.util.Message;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.ui.PostRequestGameRoute;

/**
 * The {@code POST /requestResponse} route handler.
 *
 * @author Mario Castano
 */
public class PostRequestResponseRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    // Values used in the view-model map for rendering the receivedRequest message on screen
    static final String REQUEST_VAL = "gameRequest";
    static final String VIEW_NAME = "home.ftl";
    static final String GAME_ACCEPT = "response";

    private final TemplateEngine templateEngine;

    /**
     * Constructor for the {@code GET/game} route handler.
     *
     * @param templateEngine
     *      The {@link TemplateEngine} used to render pages into HTML.
     */
    PostRequestResponseRoute(final TemplateEngine templateEngine) {
        //validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    /**
     * {@inheritDoc}
     *
     * The handler for player response to game request that has been sent. First, a user challenges another user,
     * and a request is sent consists of the player's username and the challengee's username from
     * the list of users logged onto the playerLobby.
     */
    @Override
    public String handle(Request request, Response response)
    {
        //retrieve the playerLobby object to verify that no time out has occurred
        final Session httpSession = request.session();
        final PlayerLobby playerLobby =
          httpSession.attribute(GetHomeRoute.PLAYER_LOBBY_KEY);
        final Player player = httpSession.attribute(GetHomeRoute.PLAYER_KEY);
        final Player opposingPlayer = httpSession.attribute(GetHomeRoute.CHALLENGED_USER_KEY);

        /* A null playerLobby indicates a timed out session or an illegal request on this URL.
         * In either case, we will redirect back to home.
         */
        if (playerLobby != null) {
            final Map<String, Object> vm = new HashMap<>();
            final String usernameStr = request.queryParams(REQUEST_VAL);
            vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
            switch (GAME_ACCEPT) {
                case YES:
                    PlayerLobby.gameStarted(usernameStr);
                    response.redirect(WebServer.GAME_URL);
                    break;
                case NO:
                    removePlayer(player.getUsername());
                    response.redirect(WebServer.HOME_URL);
                    break;
                //Act upon the player's response to a game request
            }
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
        else{
                response.redirect(WebServer.HOME_URL);
                halt();
                return null;
            }
    }

    private void getResponse() {

    }

    static private void removePlayer(String username) {
        PlayerLobby.removeChallenger(username);
    }
}

