/**
 * @format
 */

import 'react-native';
import React from 'react';
import App from '../App';
// Note: test renderer must be required after react-native.
import {create} from 'react-test-renderer';

describe('App component', () => {
  test('Matches the snapshot', () => {
    const button = create(<App />);
    expect(button.toJSON()).toMatchSnapshot();
  });
});
