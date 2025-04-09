// config-overrides.js
module.exports = function override(config) {
    // Modify the Webpack config
    config.module.rules = config.module.rules.map(rule => {
      if (rule.loader && rule.loader.includes('source-map-loader')) {
        return {
          ...rule,
          exclude: [/node_modules\/react-datepicker/], // Skip source maps for react-datepicker
        };
      }
      return rule;
    });
    return config;
  };