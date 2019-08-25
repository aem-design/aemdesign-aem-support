module.exports = {
  presets: [
    ['@babel/preset-env', {
      loose       : true,
      modules     : false,
      useBuiltIns : 'entry',

      corejs: {
        proposals : true,
        version   : 3,
      },
    }],
    '@babel/preset-typescript',
    'vue',
  ],

  plugins: [
    '@babel/plugin-transform-runtime',
    '@babel/proposal-class-properties',
    '@babel/proposal-object-rest-spread',
    '@babel/plugin-syntax-dynamic-import',
    ['@babel/plugin-transform-regenerator', {
      'async'           : true,
      'asyncGenerators' : false,
      'generators'      : false,
    }],
  ],
}
