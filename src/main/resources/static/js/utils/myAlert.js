// 自定义提示函数
function MyAlert(data, to) {
    // 过滤掉 localhost 地址等调试信息
    let message = data.message.replace(/http?:\/\/[^ ]*localhost[^ \n]*/gi, '').trim();

    const alertBox = $('#custom-alert');

    // 设置提示消息文本
    alertBox.text(message);

    // 根据状态设置不同的背景颜色
    if (data.status === "success") {
        alertBox.css({
            'background-color': '#d4edda', // 成功时的背景颜色（浅绿色）
            'color': '#155724' // 文字颜色
        });
    } else {
        alertBox.css({
            'background-color': '#f8d7da', // 错误时的背景颜色（浅红色）
            'color': '#721c24' // 文字颜色
        });
    }

    // 显示提示框
    alertBox.fadeIn(300);

    // 2秒后自动隐藏提示框
    setTimeout(() => {
        alertBox.fadeOut(300);
    }, 2000);

    // 如果是成功状态并且有跳转目标，则延迟跳转
    if (data.status === "success" && to) {
        setTimeout(() => {
            window.location.href = to;
        }, 2500); // 稍微延迟跳转，确保提示可见
    }
}