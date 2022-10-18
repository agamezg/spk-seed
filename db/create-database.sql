CREATE
DATABASE [coupons_datasource]
GO

USE [coupons_datasource];
GO

CREATE TABLE movprop
(
    Id          INT  NOT NULL IDENTITY,
    Name        TEXT NOT NULL,
    Description TEXT NOT NULL,
    PRIMARY KEY (Id)
);
GO

BULK INSERT INTO [movprop]
FROM './cupones.csv'
WITH (FORMAT = 'CSV',
    FIRSTROW = 0,
    FIELDTERMINATOR = ',',
    FIELDQUOTE = '"'
    ROWTERMINATOR = '0x0a')
GO
