package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.WebServer.HOME_URL;

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
      String CURRENT_PLAYER = "currentUser";
      String username = player.getUsername();
      vm.put(CURRENT_PLAYER, username);

      CheckerGame game;
      int gameIdNum = gameManager.getGameID(player.getUsername());
      game = gameManager.getGame(gameIdNum);
      Player redPlayer = game.getRedPlayer();
      Player whitePlayer = game.getWhitePlayer();
      vm.put(VIEW_MODE, Player.ViewMode.PLAY);
      session.attribute(GAME_BOARD, game.getBoard());
      if (game.getRedPlayer().getUsername().equals(username))
      {
        vm.put(RED_PLAYER, redPlayer);
        vm.put(WHITE_PLAYER, whitePlayer);
        BoardView bV = new BoardView(game.getBoard());
        vm.put(GAME_BOARD_VIEW, bV);
        LOG.config(game.getRedPlayer().getUsername() + " is player 1, red " +
                "should be on the bottom.");
      }
      if (game.getWhitePlayer().getUsername().equals(username))
      {
        vm.put(RED_PLAYER, redPlayer);
        vm.put(WHITE_PLAYER, whitePlayer);
        BoardView bV = new BoardView(game.getBoard());
        bV.flip();
        vm.put(GAME_BOARD_VIEW, bV);
        LOG.config(game.getWhitePlayer().getUsername() + " is player2, white " +
                "should" +
                " " +
                "be on the bottom");
      }
      vm.put(ACTIVE_COLOR, game.getColor());
      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    } else
    {
      response.redirect(HOME_URL);
      return "Home Redirect";
    }
  }
}
