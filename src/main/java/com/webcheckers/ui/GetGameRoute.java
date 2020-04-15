package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.*;
import spark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.model.Piece.Color.RED;
import static com.webcheckers.model.Piece.Color.WHITE;
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
  private Gson gson = new Gson();
  private final TemplateEngine templateEngine;

  public GetGameRoute(TemplateEngine templateEngine)
  {
    //validate
    Objects.requireNonNull(templateEngine,
            "templateEngine must not be null");
    this.templateEngine = templateEngine;
  }

  public synchronized Object handle(Request request, Response response)
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
      String oppUsername;
      vm.put(CURRENT_PLAYER, username);

      CheckerGame game;
      int gameIdNum = gameManager.getGameID(player.getUsername());
      game = gameManager.getGame(gameIdNum);
      Player redPlayer = game.getRedPlayer();
      Player whitePlayer = game.getWhitePlayer();
      vm.put(VIEW_MODE, Player.ViewMode.PLAY);
      //Reset Moves
      session.attribute(PostValidateMoveRoute.MOVE_LIST_ID, null);
      session.attribute(GAME_BOARD, game.getBoard());
      Piece.Color color;
      vm.put(RED_PLAYER, redPlayer);
      vm.put(WHITE_PLAYER, whitePlayer);
      if (game.getRedPlayer().getUsername().equals(username))
      {
        color = Piece.Color.RED;
        oppUsername = game.getWhitePlayer().getUsername();
        BoardView bV = new BoardView(game.getBoard());
        vm.put(GAME_BOARD_VIEW, bV);
      } else
      {
        color = Piece.Color.WHITE;
        oppUsername = game.getRedPlayer().getUsername();
        BoardView bV = new BoardView(game.getBoard());
        bV.flip();
        vm.put(GAME_BOARD_VIEW, bV);
      }
      RequireMove requireMove = new RequireMove(game.getBoard(), color);
      Map<Move.MoveStatus, List<Move>> availableMoves = requireMove.getAllMoves();
      //check me then opponent for if opponent can move.
      final Map<String, Object> modeOptions = new HashMap<>(2);
      if(availableMoves.get(Move.MoveStatus.JUMP).isEmpty() &&
              availableMoves.get(Move.MoveStatus.VALID).isEmpty())
      {
        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage", oppUsername + " has stopped you " +
                "from moving! You've lost!");
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
      } else
      {
        if(color == RED)
        {
          color = WHITE;
        } else
        {
          color = RED;
        }
        requireMove.updateColor(color);
        availableMoves = requireMove.getAllMoves();
        if(availableMoves.get(Move.MoveStatus.JUMP).isEmpty() &&
                availableMoves.get(Move.MoveStatus.VALID).isEmpty())
        {
          modeOptions.put("isGameOver", true);
          modeOptions.put("gameOverMessage", oppUsername + " cannot " +
                  "move! You win!");
          vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        }
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
