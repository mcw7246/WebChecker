package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetGameRoute.*;
import static com.webcheckers.ui.WebServer.HOME_URL;

public class GetSpectatorGameRoute implements Route
{
  public static String TURN = "turn";

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
  private Gson gson = new Gson();
  private final TemplateEngine templateEngine;

  public GetSpectatorGameRoute(TemplateEngine templateEngine)
  {
    Objects.requireNonNull(templateEngine,
            "templateEngine must not be null");
    this.templateEngine = templateEngine;
  }

  @Override
  public Object handle(Request request, Response response)
  {
    final Map<String, Object> vm = new HashMap<>();
    vm.put(GetHomeRoute.TITLE_ATTR, "Web Checkers");

    final Session session = request.session();
    final Player spectator = session.attribute(GetHomeRoute.PLAYER_KEY);

    if(spectator != null)
    {
      String redUsername = request.queryParams("watchGameRequest");
      GameManager manager = session.attribute(GetHomeRoute.GAME_MANAGER_KEY);
      String CURRENT_PLAYER = "currentUser";
      vm.put(CURRENT_PLAYER, spectator.getUsername());
      vm.put(GetGameRoute.VIEW_MODE, Player.ViewMode.SPECTATOR);
      CheckerGame game;
      int gameIdNum = manager.getGameID(redUsername);
      game = manager.getGame(gameIdNum);
      Player redPlayer = game.getRedPlayer();
      Player whitePlayer = game.getWhitePlayer();
      vm.put(RED_PLAYER, redPlayer);
      vm.put(WHITE_PLAYER, whitePlayer);
      if(game.getTurn().equals(redUsername))
      {
        session.attribute(TURN, redUsername);
        BoardView bV = new BoardView(game.getBoard());
        vm.put(GAME_BOARD_VIEW, bV);
      } else
      {
        session.attribute(TURN, whitePlayer.getUsername());
        BoardView bV = new BoardView(game.getBoard());
        bV.flip();
        vm.put(GAME_BOARD_VIEW, bV);
      }
      vm.put(ACTIVE_COLOR, game.getColor());
      vm.put(VIEWERS, manager.getViewers(gameIdNum));
      manager.addSpectator(spectator.getUsername(), gameIdNum);
      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    } else
    {
      response.redirect(HOME_URL);
      return "Home Redirect";
    }
  }
}
