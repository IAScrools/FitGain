//default:
//module.exports = {
//  root: true,
//  extends: '@react-native-community',
//};

module.exports = {
  parser: '@babel/eslint-parser',
  parserOptions: {
    babelOptions: {
      plugins: [
        '@babel/plugin-transform-react-jsx',
        '@babel/plugin-syntax-flow',
      ],
    },
  },
  plugins: ['flowtype', 'prettier'],
  extends: ['@react-native-community', 'plugin:prettier/recommended'],
  rules: {
    'flowtype/boolean-style': [2, 'boolean'],
    'flowtype/define-flow-type': 1,
    'flowtype/delimiter-dangle': 0,
    'flowtype/generic-spacing': [2, 'never'],
    'flowtype/no-mixed': 0,
    'flowtype/no-types-missing-file-annotation': 2,
    'flowtype/no-weak-types': 0,
    'flowtype/require-parameter-type': 0,
    'flowtype/require-readonly-react-props': 0,
    'flowtype/require-return-type': 0,
    'flowtype/require-valid-file-annotation': 0,
    'flowtype/semi': 0,
    'flowtype/space-after-type-colon': [2, 'always'],
    'flowtype/space-before-generic-bracket': [2, 'never'],
    'flowtype/space-before-type-colon': [2, 'never'],
    'flowtype/type-id-match': 0,
    'flowtype/union-intersection-spacing': [2, 'always'],
    'flowtype/use-flow-type': 1,
    'prettier/prettier': ['error'],
  },
  settings: {
    flowtype: {
      onlyFilesWithFlowAnnotation: false,
    },
  },
};
