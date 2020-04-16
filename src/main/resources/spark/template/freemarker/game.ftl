<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>${title} | Web Checkers</title>
  <link rel="stylesheet" href="/css/style.css">
  <link rel="stylesheet" href="/css/game.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script>
      window.gameData = {
          "gameID": ${gameID!'null'},
          "currentUser": "${currentUser}",
          "viewMode": "${viewMode}",
          "modeOptions": ${modeOptionsAsJSON!'{}'},
          "redPlayer": "${redPlayer.getUsername()}",
          "whitePlayer": "${whitePlayer.getUsername()}",
          "activeColor": "${activeColor}",
          "viewers": "${viewers}"
      };
  </script>
</head>
<body onload="initTheme()">
<div class="page">
  <h1>Web Checkers | Game View</h1>

    <#include "nav-bar.ftl" />

  <div class="body">

    <div id="help_text" class="INFO"></div>

    <div>
      <div id="game-controls">

        <fieldset id="game-info">
          <legend>Info</legend>

            <#include "message.ftl" />

          <div>
            <table data-color='RED'>
              <tr>
                <td><#if theme>
                    <img src="../img/duck/single-piece-red-duck.svg"/>
                    <#else>
                      <img src="../img/single-piece-red.svg"/>
                    </#if></td>
                <td class="name">Red</td>
              </tr>
            </table>
            <table data-color='WHITE'>
              <tr>
                <td> <#if theme>
                    <img src="../img/duck/single-piece-white-duck.svg"/>
                    <#else>
                      <img src="../img/single-piece-white.svg"/>
                    </#if></td>
                <td class="name">White</td>
              </tr>
            </table>
          </div>
        </fieldset>

        <fieldset id="game-toolbar">
          <legend>Controls</legend>
          <div class="toolbar"></div>
        </fieldset>

      </div>

      <div class="game-board">
        <table id="game-board">
          <tbody>
          <#list board.iterator() as row>
            <tr data-row="${row.getIndex()}">
                <#list row.iterator() as space>
                  <td data-cell="${space.getColumnIndex()}"
                          <#if space.isValidSpace() >
                            class="Space"
                          </#if>
                  >
                      <#if space.getPiece()??>
                        <div class="Piece"
                             id="piece-${row.getIndex()}-${space.getColumnIndex()}"
                             data-type="${space.getPiece().getType()}"
                             data-color="${space.getPiece().getColor()}">
                        </div>
                      </#if>
                  </td>
                </#list>
            </tr>
          </#list>
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <#if notReplay>
    <h3>Current Spectators: ${viewers}</h3>
  </#if>
</div>

<#if theme>
<audio id="audio" src="https://www.soundjay.com/misc/sounds/squeeze-toy-5.mp3"
       autostart="false"></audio>
<#else>
<audio id="audio" src="https://www.soundjay.com/misc/bell-ringing-05.mp3"
       autostart="false"></audio>
</#if>

<script data-main="/js/game/index" src="/js/require.js"></script>
<script data-main="/js" src="/js/initTheme.js"></script>

</body>
</html>
