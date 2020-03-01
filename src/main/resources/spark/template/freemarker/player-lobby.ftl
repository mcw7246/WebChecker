<#if signIn>
    <div id="player-lobby">
            <#list usernames as username>
                <p>${username}:
                <form action='./requestGame' method="POST">
                <button type="submit" value=${username}>Request Game</button>
            </form>
            </#list>
    </div>
<#else>
    <div id="player-lobby">Currently logged in Players: ${playerNum}</div>
</#if>