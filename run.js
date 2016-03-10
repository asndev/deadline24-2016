'strict mode';

const assert = require('assert');

const run = (inputs) => {
  return inputs.map(tests => {
    return {
      filename: tests[0].filename,
      scores: tests.map((test) => {
        return solve(test);
      })
    };
  });
};

const validateCell = (matrix, colIndex, rowIndex, K) => {
  var result = true;
  for (var k = 0; k < K; ++k) {
    if (!(matrix[colIndex - 1] && matrix[colIndex - 1][rowIndex + k] &&
      matrix[colIndex] && matrix[colIndex][rowIndex + k] &&
      matrix[colIndex + 1] && matrix[colIndex + 1][rowIndex + k])) {
      result = false;
      break;
    }
  }

  if (result) {
    result = [colIndex, rowIndex];
  }

  return result;
};

const computeScore = (N, M, K, drops) => {
  var matrix = [];

  console.log(`draw all drops: ${drops.length} with matrix: ${N}x${M}`);
  for (var m = 0; m < M; ++m) {
    matrix[m] = [];
    for (var n = 0; n < N; ++n) {
      matrix[m][n] = false;
    }
  }

  drops.forEach(d => {
    var col = d[0];
    var row = d[1];

    for (var k = 0; k < K; ++k) {
      matrix[col - 1][row + k] = true;
      matrix[col][row + k] = true;
      matrix[col + 1][row + k] = true;
    }
  });

  return matrix
    .map(e => {
      return e.reduce((c, p) => {
        return c + p;
      }, 0);
    })
    .reduce((c, p) => {
      return c + p;
    }, 0);
};

// grid -> int
const solve = (input) => {
  const {K, N, M, matrix, filename} = input;

  var drops = [];

  for (var m = 0; m < M; ++m) {
    for (var n = 0; n < N; ++n) {
      if (matrix[m][n]) {
        var drop = validateCell(matrix, m, n, K);
        if (drop) {
          drops.push(drop);
        }
      }
    }
  }
  console.log('found drop locations');
  return computeScore(N, M, K, drops);
};

var fs = require('fs');

console.log('Parsing input ...');
const inputs = require('./parser_asn');

console.log('Running algorithm');
run(inputs).forEach((e, index) => {
  var filename = `./asn/${e.filename}.ans`;
  var result = '';
  e.scores.forEach(score => {
    result += score + '\n';
  });

  fs = require('fs');
  fs.writeFile(filename, result, function(err) {
    if (err) {
      console.log(`Error while writing result from ${e.filename} into file: ${filename}`);
      console.error(err);
    }
  });
});
