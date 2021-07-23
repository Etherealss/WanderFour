// const path = require("path");
// function resolve(dir) {
//   return path.join(__dirname, dir);
// }

module.exports = {
  publicPath: process.env.NODE_ENV === 'production'?'./':'/',  // 根目录（防止打包后出错）
  outputDir: 'dist',      // 打包后的目录名称
  assetsDir: 'assets',    // 静态资源目录名称
  // parallel: false,
  configureWebpack: {
    resolve: {
      alias: {
        // @ 默认是 src，设置别名后，就不用'./'了
        'assets': '@/assets',
        'common': '@/common',
        'components': '@/components',
        'network': '@/network',
        'views': '@/views'
      }
    }
  }
}
