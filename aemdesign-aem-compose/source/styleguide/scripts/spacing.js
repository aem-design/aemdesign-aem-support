let fs = require('fs');

let table = `<table class="styleguide-table"><tr class="styleguide-table__row styleguide-table__row--header"><th class="styleguide-table__header styleguide-table__header--token">Token</th><th class="styleguide-table__header">rem</th><th>px</th></tr>`;

let data = [
  { name: "$spacing-2xs", rem: "0.3125", px: "5px" },
  { name: "$spacing-xs", rem: "0.625", px: "10px" },
  { name: "$spacing-sm", rem: ".9375", px: "15px" },
  { name: "$spacing-md", rem: "1.25", px: "20px" },
  { name: "$spacing-lg", rem: "1.5625", px: "25px" },
  { name: "$spacing-2xl", rem: "1.875", px: "30px" },
  { name: "$spacing-3xl", rem: "2.1875", px: "35px" },
  { name: "$spacing-4xl", rem: "2.5", px: "40px" }
];

data.map((row) => {
  table += `<tr class="styleguide-table__row">`;
  Object.entries(row).forEach((cell, index) => {
    const cellClass = index === 0 ? 'styleguide-table__cell--code' : '';
    table += `<td class="styleguide-table__cell ${cellClass}">${cell[1]}</td>`;
  });
  table += `</tr>`;
});

table += `</table>`;

fs.writeFile ("../html/spacing.html", table, function(err) {
    if (err) throw err;
    console.log('complete');
  }
);

