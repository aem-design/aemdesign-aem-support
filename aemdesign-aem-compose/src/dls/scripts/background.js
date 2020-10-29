let fs = require('fs');

let template = '<div class="row backgrounds">';
const backgrounds = [
  { class: "bg-grey-1", modifier: "Component Style Modifier / Background / Greys / Grey 1" },
  { class: "bg-grey-2", modifier: "Component Style Modifier / Background / Greys / Grey 2" },
  { class: "bg-grey-3", modifier: "Component Style Modifier / Background / Greys / Grey 3" },
  { class: "bg-grey-4", modifier: "Component Style Modifier / Background / Greys / Grey 4" },
  { class: "bg-grey-5", modifier: "Component Style Modifier / Background / Greys / Grey 5" },
  { class: "bg-charcoal", modifier: "Component Style Modifier / Background / Primary / Charcoal" },
  { class: "bg-charcoal-pattern", modifier: "Component Style Modifier / Background / Primary / Charcoal Pattern" },
  { class: "bg-red", modifier: "Component Style Modifier / Background / Primary / Red" },
  { class: "bg-red-50", modifier: "Component Style Modifier / Background / Primary / Red (50% Opacity)" },
  { class: "bg-red-pattern", modifier: "Component Style Modifier / Background / Primary / Red Pattern" },
  { class: "bg-yellow", modifier: "Component Style Modifier / Background / Primary / Yellow" },
  { class: "bg-yellow-50", modifier: "Component Style Modifier / Background / Primary / Yellow (50% Opacity)" },
  { class: "bg-yellow-pattern", modifier: "Component Style Modifier / Background / Primary / Yellow Pattern" },
  { class: "bg-blue", modifier: "Component Style Modifier / Background / Secondary / Blue" },
  { class: "bg-blue-50", modifier: "Component Style Modifier / Background / Secondary / Blue (50% Opacity)" },
  { class: "bg-green", modifier: "Component Style Modifier / Background / Secondary / Green" },
  { class: "bg-green-50", modifier: "Component Style Modifier / Background / Secondary / Green (50% Opacity)" },
  { class: "bg-purple", modifier: "Component Style Modifier / Background / Secondary / Purple" },
  { class: "bg-purple-50", modifier: "Component Style Modifier / Background / Secondary / Purple (50% Opacity)" },
  { class: "bg-turquoise", modifier: "Component Style Modifier / Background / Secondary / Turquoise" },
  { class: "bg-turquoise-50", modifier: "Component Style Modifier / Background / Secondary / Turquoise (50% Opacity)" }
];



backgrounds.map( (background) => {
  template += `<div class="background"><h4>Modifier: ${background.modifier} -- Class : ${background.class}</h4><span class="${background.class}"></span></div>`;
});

template += '</div>';

fs.writeFile ("../html/background.html", template, function(err) {
    if (err) throw err;
    console.log('complete');
  }
);
