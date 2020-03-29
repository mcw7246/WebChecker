package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Move;
import spark.*;

import java.util.Objects;

/**
 * This action submits a single move for validation. The server must keep
 * track of each proposed move for a single turn in the user's game state. It
 * sends INFO if the move is valid and an ERROR if not. The text should say
 * why the move is invalid.
 *
 * Query's Params for actionData Move (As a class).
 *
 * @author Austin Miller 'akm8654'
 */
public class PostValidateMoveRoute implements Route
{
  public enum MoveStatus {INVALID_SPACE, VALID, OCCUPIED, TOO_FAR}

  Gson gson = new Gson();
  private static final String ACTION_DATA = "actionData";

  private final TemplateEngine templateEngine;

  PostValidateMoveRoute(final TemplateEngine templateEngine)
  {
    Objects.requireNonNull(templateEngine, "templateEngine must not be" +
            "null");
    this.templateEngine = templateEngine;
  }

  @Override
  public String handle(Request request, Response response)
  {
    final Session httpSession = request.session();
    final String moveStr = request.queryParams(ACTION_DATA);
    final Move move = gson.fromJson(moveStr, Move.class);

  }
}
