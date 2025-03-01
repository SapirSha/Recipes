<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Recipes</title>
  <style>
    #imageSlideshow {
      position: fixed;
      top: 0;
      left: 0;
      width: 100vw;
      height: 100vh;
      overflow: hidden;
      z-index: -1;  /* Background */
    }
    
    .slide {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: opacity 1s ease-in-out;
      opacity: 0;
    }
    
    .slide.active {
      opacity: 1;
    }
    
    .content {
      position: relative;
      z-index: 1;
      color: white;
      padding: 2rem;
    }
  </style>
</head>
<body>
  <%@ include file="common/header.jsp" %>
  
  <div class="content">
    <!-- PLACE THINGS HERE -->
  </div>
  
  <%@ include file="common/LoginSignupModal.jsp" %>
  
  <div id="imageSlideshow">
    <img class="slide" src="https://images.pexels.com/photos/905847/pexels-photo-905847.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2" alt="Slide 1">
    <img class="slide" src="https://images.pexels.com/photos/1639562/pexels-photo-1639562.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2" alt="Slide 2">
    <img class="slide" src="https://images.pexels.com/photos/2098085/pexels-photo-2098085.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2" alt="Slide 3">
    <img class="slide" src="https://images.pexels.com/photos/3551717/pexels-photo-3551717.png?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2" alt="Slide 4">
    <img class="slide" src="https://images.pexels.com/photos/2347311/pexels-photo-2347311.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2" alt="Slide 5">
  </div>
  
  <script>
    let slideIndex = 0;
    
    function showSlides() {
      const slides = document.getElementsByClassName("slide");
      for (let i = 0; i < slides.length; i++) {
        slides[i].classList.remove("active");
      }
      slideIndex++;
      if (slideIndex > slides.length) {
        slideIndex = 1;
      }
      slides[slideIndex - 1].classList.add("active");
      
      setTimeout(showSlides, 3000);
    }
    
    window.addEventListener('load', showSlides);
  </script>
</body>
</html>
