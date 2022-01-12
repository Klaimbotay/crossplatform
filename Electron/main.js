const path = require('path');
const url = require('url');
const {app, BrowserWindow} = require('electron');
const request = require('request');

let win;

function createWindow() {
  win = new BrowserWindow({
    width: 700,
    height: 500,
    icon: __dirname + "/img/icon.png",
    webPreferences: {
      nodeIntegration: true,
      enableRemoteModule: true,
      contextIsolation: false
    }
  })

  win.loadURL(url.format({
    pathname: path.join(__dirname, 'index.html'),
    protocol: 'file',
    slashes: true
  }));

  //win.webContents.openDevTools();

  win.on('closed', () => {
    win = null;
  });
}

app.on('ready', createWindow);
