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
  public static final String GAME_BOARD = "board";
  public static final String VIEW_NAME = "game.ftl";

  //Attributes
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
  private final TemplateEngine templateEngine;
  private String CURRENT_PLAYER = "currentUser";
  private String OPPONENT_PLAYER;
  private PlayerLobby lobby;
  private GameManager gameManager;

  public GetGameRoute(TemplateEngine templateEngine, PlayerLobby lobby)
  {
    //validate
    Objects.requireNonNull(templateEngine,
            "templateEngine must not be null");
    Objects.requireNonNull(templateEngine,
            "templateEngine must not be null");
    this.templateEngine = templateEngine;
    this.lobby = lobby;
  }

  public Object handle(Request request, Response response)
  {
    LOG.config("GetGameRoute invoked");
    final Map<String, Object> vm = new HashMap<>();
    vm.put(GetHomeRoute.TITLE_ATTR, "Web Checkers");

    final Session httpSession = request.session();
    final Player player = httpSession.attribute(GetHomeRoute.PLAYER_KEY);
    if (player != null)
    {
      this.gameManager = httpSession.attribute(GetHomeRoute.GAME_MANAGER_KEY);
      GameManager.PLAYERS number = gameManager.getNumber(player.getUsername());
      vm.put(CURRENT_PLAYER, player.getUsername());
      final Player opponent = gameManager.getOpponent(player.getUsername());
      CheckerGame checkersGame;
      int gameIdNum = gameManager.getGameID(player.getUsername());
      checkersGame = gameManager.getGame(gameIdNum);
      vm.put(VIEW_MODE, Player.ViewMode.PLAY);

      if (checkersGame.getRedPlayer().equals(player))
      {
        vm.put(RED_PLAYER, player);
        vm.put(WHITE_PLAYER, opponent);
        vm.put(GAME_BOARD, checkersGame.getBoardView(false));
        LOG.config("you are player 1, red should be on the bottom.");
      } else
      {
        vm.put(RED_PLAYER, opponent);
        vm.put(WHITE_PLAYER, player);
        vm.put(GAME_BOARD, checkersGame.getBoardView(true));
        info("you are player2, white should be on the bottom");
      }
      vm.put(ACTIVE_COLOR, Piece.Color.RED);

      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    } else
    {
      response.redirect(HOME_URL);
      return null;
    }
  }
}
