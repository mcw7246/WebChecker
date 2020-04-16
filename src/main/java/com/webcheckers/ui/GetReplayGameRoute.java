package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.application.ReplayManager;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.ui.GetGameRoute.*;
import static com.webcheckers.ui.GetGameRoute.GAME_BOARD_VIEW;
import static com.webcheckers.ui.WebServer.HOME_URL;
import static com.webcheckers.util.Message.info;

public class GetReplayGameRoute implements Route
{
  private static final String GAME_ID = "gameID";
  private static final String RED_PLAYER = "redPlayer";
  private static final String MODE_OPTIONS_AS_JSON = "modeOptionsAsJSON";
  private static final String HAS_NEXT = "hasNext";
  private static final String HAS_PREVIOUS = "hasPrevious";
  private final ReplayManager rManager;
  private Gson gson = new Gson();
  private final TemplateEngine engine;

  public GetReplayGameRoute(TemplateEngine templateEngine,
                            ReplayManager replayManager)
  {
    this.rManager = replayManager;
    Objects.requireNonNull(templateEngine,
            "templateEngine must not be null");
    this.engine = templateEngine;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception
  {
    final Map<String, Object> vm = new HashMap<>();
    vm.put(GetHomeRoute.TITLE_ATTR, "Web Checkers");

    final Session session = request.session();
    final Player watcher = session.attribute(GetHomeRoute.PLAYER_KEY);

    String theme = session.attribute("theme");
    if (theme != null)
    {
      vm.put("theme", true);
    } else
    {
      vm.put("theme", false);
    }

    if (watcher != null)
    {
      String gameIDStr = request.queryParams("gameID");
      int gameID;
      if (gameIDStr != null)
      {
        gameID = Integer.parseInt(gameIDStr);
        session.attribute(GAME_ID, gameID);

      } else
      {
        gameID = session.attribute(GAME_ID);
      }
      String CURRENT_PLAYER = "currentUser";
      String username = watcher.getUsername();
      vm.put(CURRENT_PLAYER, username);
      vm.put(GetGameRoute.VIEW_MODE, Player.ViewMode.REPLAY);
      GameManager manager = session.attribute(GetHomeRoute.GAME_MANAGER_KEY);

      int move = rManager.getMove(username, gameID);
      boolean hasNext = move > 0;
      boolean hasPrevious = move < rManager.maxMoves(gameID)-1;
      Map<String, Object> modeOptions = new HashMap<>();
      modeOptions.put(HAS_NEXT, hasNext);
      modeOptions.put(HAS_PREVIOUS, hasPrevious);
      vm.put(MODE_OPTIONS_AS_JSON, gson.toJson(modeOptions));

      CheckerGame game = rManager.getGameAtMove(gameID, move);
      vm.put(RED_PLAYER, game.getRedPlayer());
      vm.put(WHITE_PLAYER, game.getWhitePlayer());
      vm.put(ACTIVE_COLOR, game.getColor());
      if (game.getColor() == Piece.Color.RED)
      {
        BoardView bV = new BoardView(game.getBoard());
        vm.put(GAME_BOARD_VIEW, bV);
      } else
      {
        BoardView bV = new BoardView(game.getBoard());
        bV.flip();
        vm.put(GAME_BOARD_VIEW, bV);
      }
      if (gameIDStr != null)
      {
        vm.put("message",
                info("You have started viewing " + game.getRedPlayer().getUsername() +
                        "against " + game.getWhitePlayer().getUsername() +
                        "!"));
      }
      return null;
    } else
    {
      response.redirect(HOME_URL);
      return "Home Redirect";
    }
  }
}
