 <div class="navigation">
  <#if currentUser??>
    <a href="/">my home</a> |
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser}]</a>
    </form>
      | <a href="" onclick="ChangeTheme()">switch theme</a>
  <#else>
      <form id="signin" action="/signin" method="post">
          <a href="/signin">sign in</a>
      </form> |
      <a href="" onclick="ChangeTheme()">switch theme</a>
  </#if>
     <script data-main="/js" src="/js/ChangeTheme.js"></script>
 </div>
