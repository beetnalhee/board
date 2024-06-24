<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="utf-8" %>
<jsp:useBean id="member" class="com.ezen.member.dto.Member" scope="session"/>


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

        <div class="bg-pattern text-primary text-opacity-50 opacity-25 w-100 h-100 start-0 top-0 position-absolute">
        </div>
        <div class="bg-gradientwhite flip-y w-50 h-100 start-50 top-0 translate-middle-x position-absolute"></div>
        <div class="container pt-11 pt-lg-14 pb-9 position-relative z-1">
          <div class="row align-items-center justify-content-center">
            <div class="col-md-6 col-sm-8 mt-3">
              <h3>${member.name}님의 회원가입이 완료되었습니다</h3>
              <p class="mb-2 w-lg-75">
                이젠의 모든 서비스를 즐겨보세요
              </p>

              <div class="width-50x pt-1 bg-primary mb-3"></div>

              <div class="position-relative">
                <div>
                  <form name="register-form" class="needs-validation" novalidate action="#" method="post" >
                    <div class="input-icon-group mb-3">
                      <span class="input-icon">
                        <i class="bx bx-user-pin"></i>
                      </span>
                      <input type="text" class="form-control"  value="${member.id}" disabled>
                    </div>

                    <div class="input-icon-group mb-3">
                      <span class="input-icon">
                        <i class="bx bx-user"></i>
                      </span>
                      <input type="text" class="form-control"  disabled value="${member.name}">
                    </div>

                    <div class="input-icon-group mb-3">
                      <span class="input-icon">
                        <i class="bx bx-envelope"></i>
                      </span>
                      </span>
                      <input type="text" class="form-control"  disabled value="${member.email}">
                    </div>

                    <div class="input-icon-group mb-3">
                      <span class="input-icon">
                        <i class="bx bx-envelope"></i>
                      </span>
                      </span>
                      <input type="text" class="form-control"  disabled value="${member.regdate}">
                    </div>

                    <div class="d-grid">
                      <a href="login.jsp" class="btn btn-primary" type="submit">로그인 하러 가기</a>
                      <a href="/" class="btn btn-primary" type="submit">홈페이지로 이동</a>
                    </div>
                  </form>


                </div>
              </div>
            </div>
          </div>
        </div>


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
