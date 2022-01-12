document.querySelector('#searchBtn').addEventListener('click', () => {
  search();
})

document.querySelector('#saveBtn').addEventListener('click', () => {
  save();
})

function search() {
  const request = require('request');
  let link = document.getElementById('link').value;
  let options = {json: true};
  request(link, options, (error, res, body) => {
    if (error) {
      return console.log(error);
    };
    if (!error && res.statusCode == 200) {
      let json = JSON.stringify(body, null, 3);
      document.getElementById('text').value = json;
    };
  });
}

function save() {
  const {app, BrowserWindow} = require('electron');
  let dialog = require('electron').remote;
  let fs = require('fs');
  let file = document.getElementById('text').value
  let fileName = "jsonText"

  if(check(document.getElementById('text').value)) {
    let content = document.getElementById('text').value;
    fs.writeFile(fileName + '.json', content, function(err) {
      if(err) return console.log(err)
    })
  } else {
    alert( "This is not JSON" );
  }
}

function check(str) {
  try {
    JSON.parse(str)
  } catch (e) {
    return false
  }
    return true;
}
