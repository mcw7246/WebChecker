package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.WebServer.HOME_URL;
import static com.webcheckers.util.Message.info;

public class GetGameRoute implements Route
{
  //Constants
  public static final String VIEW_MODE = "viewMode";
  public static final String RED_PLAYER = "redPlayer";
  public static final String WHITE_PLAYER = "whitePlayer";
  public static final String ACTIVE_COLOR = "activeColor";
  public static final String GAME_BOARD_VIEW = "board";
  public static final String VIEW_NAME = "game.ftl";
  public static final String GAME_BOARD = "board_actual";

  private Player redPlayer;
  private Player whitePlayer;

  //Attributes
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
  private final TemplateEngine templateEngine;

  public GetGameRoute(TemplateEngine templateEngine)
  {
    //validate
    Objects.requireNonNull(templateEngine,
            "templateEngine must not be null");
    this.templateEngine = templateEngine;
  }

  public Object handle(Request request, Response response)
  {
    LOG.config("GetGameRoute invoked");
    final Map<String, Object> vm = new HashMap<>();
    vm.put(GetHomeRoute.TITLE_ATTR, "Web Checkers");

    final Session session = request.session();
    final Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
    if (player != null)
    {
      GameManager gameManager = session.attribute(GetHomeRoute.GAME_MANAGER_KEY);
      GameManager.PLAYERS number = gameManager.getNumber(player.getUsername());
      String CURRENT_PLAYER = "currentUser";
      vm.put(CURRENT_PLAYER, player.getUsername());
      final Player opponent = gameManager.getOpponent(player.getUsername());
      CheckerGame checkersGame;
      int gameIdNum = gameManager.getGameID(player.getUsername());
      checkersGame = gameManager.getGame(gameIdNum);
      this.redPlayer = checkersGame.getRedPlayer();
      this.whitePlayer = checkersGame.getWhitePlayer();
      vm.put(VIEW_MODE, Player.ViewMode.PLAY);
      session.attribute(GAME_BOARD, checkersGame.getBoard());
      if (checkersGame.getRedPlayer().getUsername().equals(redPlayer.getUsername()))
      {
        vm.put(RED_PLAYER, redPlayer);
        vm.put(WHITE_PLAYER, whitePlayer);
        vm.put(GAME_BOARD_VIEW, checkersGame.getBoardView(false));
        LOG.config("you are player 1, red should be on the bottom.");
      }
      if(checkersGame.getWhitePlayer().getUsername().equals(whitePlayer.getUsername()))
      {
        vm.put(RED_PLAYER, redPlayer);
        vm.put(WHITE_PLAYER, whitePlayer);
        vm.put(GAME_BOARD_VIEW, checkersGame.getBoardView(true));
        info("you are player2, white should be on the bottom");
      }
      vm.put(ACTIVE_COLOR, checkersGame.getColor());
      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    } else
    {
      response.redirect(HOME_URL);
      return "Home Redirect";
    }
  }
}
