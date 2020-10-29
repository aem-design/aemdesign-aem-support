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

    '@babel/typescript',
    'vue',
  ],

  plugins: [
    '@babel/transform-runtime',
    '@babel/proposal-class-properties',
    '@babel/proposal-object-rest-spread',
    '@babel/syntax-dynamic-import',
    ['@babel/transform-regenerator', {
      'async'           : true,
      'asyncGenerators' : false,
      'generators'      : false,
    }],
  ],
}
