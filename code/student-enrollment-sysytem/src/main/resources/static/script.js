document.getElementById('refreshCaptcha').addEventListener('click', function() {
    const captchaImage = document.getElementById('captchaImage');
    // 加上时间戳以防止缓存
    captchaImage.src = '/information/captcha?' + new Date().getTime();
});