
var swiperOptions = {
    slidesPerView: 3,
    centeredSlides:true,
    speed: 300,
    watchSlidesProgress: true,
    on: {
        progress: function() {
            var swiper = this;
            // console.log(swiper.progress)
            for (var i = 0; i < swiper.slides.length; i++) {
                let progress=swiper.slides[i].progress;
                // swiper.slides[i].querySelector(".slide-inner").style.opacity =1.0-.5*Math.abs(progress);
                swiper.slides[i].querySelector(".slide-inner").style.transform ="scale("+(1.0-.5*Math.abs(progress))+")";
            }

        },
        touchStart: function() {
            var swiper = this;
            for (var i = 0; i < swiper.slides.length; i++) {
                swiper.slides[i].style.transition = "";
            }
        },
        setTransition: function(speed) {
            var swiper = this;
            for (var i = 0; i < swiper.slides.length; i++) {
                swiper.slides[i].style.transition = speed + "ms";
                swiper.slides[i].querySelector(".slide-inner").style.transition =speed + "ms";
            }
        }
    }
};

var swiper = new Swiper(".swiper-container", swiperOptions);
    
      