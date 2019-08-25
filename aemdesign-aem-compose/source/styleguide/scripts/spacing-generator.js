var fs = require('fs');

var data = {};
data.notations = "The classes are named using the format <i style=\"color: red\">{property}{sides}-{size}</i> for xs and <i style=\"color: red\">{property}{sides}-{breakpoint}-{size}</i> for sm, md, lg, and xl.";
data.margins = [];
data.paddings = [];
data.responsives_paddings = [];
data.responsives_margins = [];


var sides = [
  {value:"t",name:"top"},
  {value:"b",name:"bottom"},
  {value:"l",name:"left"},
  {value:"r",name:"right"},
  {value:"x",name:"left & right"},
  {value:"y",name:"bottom & top"},
  {value:"",  name:"all sides"}
],
properties = [{name:"margin",value:"m"}, {name:"padding",value:"p"}],
breakpoints = ["-sm", "-md", "-lg", "-xl"],
sizes = ["-0", "-1", "-2", "-3", "-4", "-5"];

var setDataArray = function(data, property) {
  sides.map(function (side) {
    sizes.map(function (size) {
      data.push({
        "name": property.name + " " + side.name,
        "value": property.value + side.value + size
      });
    })
  });
  return data;
};

var setDataWithBreakpoints = function(data, property) {
  sides.map(function (side) {
    sizes.map(function (size) {
      breakpoints.map(function (breakpoint) {
        data.push({
          "name": property.name + " " + side.name + " " + breakpoint,
          "value": property.value + side.value + breakpoint + size
        });
      });
    })
  });
  return data;
};

//Margin array
data.margins = setDataArray([], properties[0]);

//Padding array
data.paddings = setDataArray([], properties[1]);

//Responsive arrays
data.responsives_paddings = setDataWithBreakpoints([], properties[1]);
data.responsives_margins = setDataWithBreakpoints([], properties[0]);


console.log(data);
fs.writeFile ("../_patterns/02-styleguide/spacing.json", JSON.stringify(data), function(err) {
    if (err) throw err;
    console.log('complete');
  }
);
