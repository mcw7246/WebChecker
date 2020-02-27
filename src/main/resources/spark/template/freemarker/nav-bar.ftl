 <div class="navigation">
  <#if currentUser??>
    <a href="/">my home</a> |
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.name}]</a>
    </form>
  <#else>
      <form id="signin" action="/signin" method="post">
          <a href="/signin">sign in</a>
      </form>

  </#if>
 </div>
