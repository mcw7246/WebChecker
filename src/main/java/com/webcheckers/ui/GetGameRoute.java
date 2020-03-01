package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetGameRoute
{
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    private final TemplateEngine templateEngine;
    private String CURRENT_PLAYER;
    private String OPPONENT_PLAYER;
    private PlayerLobby playerLobby;

    public GetGameRoute(TemplateEngine templateEngine)
    {
        //validate
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }


    /*
    public Object handle(Request request, Response response)
    {
        LOG.config("GetGameRoute invoked");
        final Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Web Checkers");

        final Session httpSession = request.session();
        final Player player = httpSession.attribute("Player");
        vm.put(CURRENT_PLAYER, player);


        final Player player2 = playerLobby.getOpponent(user);
        final CheckersGame checkersGame = playerLobby.getGame(user, player2);
        vm.put(VIEW_MODE, Player.ViewMode.PLAY);

        if(checkersGame.getRed().equals(user)){
            vm.put(RED_PLAYER, user);
            vm.put(WHITE_PLAYER, player2);
            vm.put(GAME_BOARD, checkersGame.getBoardView());
            vm.put(MESSAGE, Message.info("you are player 1, red should be on the bottom."));
        }
        else{
            vm.put(RED_PLAYER, player2);
            vm.put(WHITE_PLAYER, user);
            vm.put(GAME_BOARD, checkersGame.getFlippedBoardView());
            vm.put(MESSAGE, Message.info("you are player2, white should be on the bottom"));
        }
        vm.put(ACTIVE_COLOR, Piece.color.RED);

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));

    }
    */
}
