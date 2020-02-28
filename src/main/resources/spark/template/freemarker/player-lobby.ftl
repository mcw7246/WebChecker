<#if signin??>
    <div id="player-lobby"> </div>
<#else>
    <div id="player-lobby" style="display:none">
        <table style="width:0"
        <#list usernames as username>
            <tr>
                <td>
                    ${username}
                </td>
                <td>
                    <form action='./requestGame' method="POST">
                        <button type="submit" value=${username}>Request
                            Game</button>
                    </form>
                </td>
            </tr>
        </#list>
    </div>
</#if>