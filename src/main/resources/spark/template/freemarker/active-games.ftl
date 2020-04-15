<#if signIn>
        <div id="active-games">
            <h2>Currently active games!</h2>
            <#list games as game>
                <p>${game.getRedPlayer().getUsername()} vs. ${game
                    .getWhitePlayer().getUsername()}:
                <form action='./spectator/game' method="GET">
                    <button type="submit" name="watchGameRequest"
                            value=${game.getRedPlayer().getUsername()}>Watch
                        Game</button>
                </form>
            </#list>
        </div>
<#else>
    <div id="player-lobby">Currently active Games: ${gameNum}</div>
</#if>