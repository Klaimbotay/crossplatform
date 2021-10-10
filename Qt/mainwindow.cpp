#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QtNetwork/QNetworkAccessManager>
#include <QtNetwork/QNetworkRequest>
#include <QtNetwork/QNetworkReply>
#include <QUrl>
#include <QJsonDocument>
#include <QFile>
#include <QFileDialog>
#include <QMessageBox>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    manager = new QNetworkAccessManager(this);
}

MainWindow::~MainWindow()
{
    delete ui;
}


void MainWindow::on_pushButton_2_clicked()
{
    QNetworkAccessManager networkManager;

    QUrl url(ui->lineEdit->text());
    QNetworkRequest request;
    request.setUrl(url);

    QNetworkReply *reply = networkManager.get(request);

    QEventLoop loop;
    connect(reply, SIGNAL(finished()), &loop, SLOT(quit()));
    connect(reply, SIGNAL(error(QNetworkReply::NetworkError)), &loop, SLOT(quit()));
    loop.exec();

    QByteArray bts = reply->readAll();
    ui->plainTextEdit->setPlainText(QString(bts));
}


void MainWindow::on_pushButton_clicked()
{
    QString data = ui->plainTextEdit->toPlainText();
    QJsonDocument doc = QJsonDocument::fromJson(data.toUtf8());
    if (doc.isNull())
    {
        QMessageBox messageBox;
        messageBox.critical(0,"Error","It is not JSON text or something wrong!");
        messageBox.setFixedSize(500,200);
    }
    else
    {
        QString fileName = QFileDialog::getSaveFileName(this, tr("Save File"),
                                   "./..",
                                   tr("JSON (*.json)"));
        QFile jsonFile(fileName);
        jsonFile.open(QFile::WriteOnly);
        jsonFile.write(doc.toJson());
    }
}

