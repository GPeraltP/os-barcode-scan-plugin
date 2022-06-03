var exec = require('cordova/exec');

exports.getBarcodeScan = function (success, error) {
    exec(success, error, 'BarcodeScan', 'getBarcodeScan', []);
};
