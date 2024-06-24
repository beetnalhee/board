<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>JSP 실습</title>
  <!-- Bootstrap core CSS -->
  <link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom styles for this template -->
  <link href="/css/simple-sidebar.css" rel="stylesheet">
</head>

<body>
  <!-- Wrapper Start -->
  <div class="d-flex" id="wrapper">
    
    <!-- Sidebar Start-->
    <jsp:include page="/module/sidebar.jsp"/>
    <!-- /#sidebar-wrapper End-->

    <!-- Page Content Start-->
    <div id="page-content-wrapper">

      <!-- Nav Start -->
      <jsp:include page="/module/nav.jsp"/>
       <!-- Nav End -->

        <!-- Contents Start -->
      <div class="container-fluid">

        <h1 class="mt-4">김희주의 게시판</h1>
        <p>김희주게시판에 오신것을 환영합니다</p>

      </div>
      <!-- Contents End -->
    </div>
    <!-- /#page-content-wrapper End-->
      </div>
  <!-- /#wrapper End-->
  

  <!-- Bootstrap core JavaScript -->
  <script src="/vendor/jquery/jquery.min.js"></script>
  <script src="/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Menu Toggle Script -->
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>
</body>
</html>
