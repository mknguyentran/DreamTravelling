USE [master]
GO
/****** Object:  Database [DreamTravelling]    Script Date: 6/22/2020 1:14:37 AM ******/
CREATE DATABASE [DreamTravelling]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DreamTravelling', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\DreamTravelling.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'DreamTravelling_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\DreamTravelling_log.ldf' , SIZE = 2048KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [DreamTravelling] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DreamTravelling].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DreamTravelling] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DreamTravelling] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DreamTravelling] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DreamTravelling] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DreamTravelling] SET ARITHABORT OFF 
GO
ALTER DATABASE [DreamTravelling] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [DreamTravelling] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DreamTravelling] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DreamTravelling] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DreamTravelling] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DreamTravelling] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DreamTravelling] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DreamTravelling] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DreamTravelling] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DreamTravelling] SET  DISABLE_BROKER 
GO
ALTER DATABASE [DreamTravelling] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DreamTravelling] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DreamTravelling] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DreamTravelling] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DreamTravelling] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DreamTravelling] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DreamTravelling] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DreamTravelling] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [DreamTravelling] SET  MULTI_USER 
GO
ALTER DATABASE [DreamTravelling] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DreamTravelling] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DreamTravelling] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DreamTravelling] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [DreamTravelling] SET DELAYED_DURABILITY = DISABLED 
GO
USE [DreamTravelling]
GO
/****** Object:  UserDefinedFunction [dbo].[TOTAL]    Script Date: 6/22/2020 1:14:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Function [dbo].[TOTAL](@orderID int)
Returns float
AS
BEGIN
	DECLARE @result float
	DECLARE @discountPercentage int
	DECLARE @discountCode varchar(30)

	SET @result = (
		Select SUM(t.Price * d.Amount)
		From OrderDetail d, Tour t
		Where d.OrderID = @orderID AND d.TourID = t.ID
	)

	SET @discountCode = (
		Select DiscountCode
		From Orders
		Where ID = @orderID
	)

	SET @discountPercentage = (
		Select DiscountPercentage
		From DiscountCode
		Where ID = @discountCode
	)

	IF @discountPercentage IS NOT NULL
	BEGIN
		Set @result = (@result / 100) * (100 - @discountPercentage)
	END

	Set @result = ROUND(@result,2)
	RETURN @result;
END
GO
/****** Object:  Table [dbo].[Account]    Script Date: 6/22/2020 1:14:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[Email] [varchar](50) NOT NULL,
	[Password] [varchar](50) NULL,
	[Role] [int] NULL,
	[Name] [nvarchar](50) NULL,
 CONSTRAINT [PK_Account] PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DiscountCode]    Script Date: 6/22/2020 1:14:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DiscountCode](
	[ID] [varchar](30) NOT NULL,
	[ExpiryDate] [datetime] NULL,
	[DiscountPercentage] [int] NULL,
 CONSTRAINT [PK_DiscountCode] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Location]    Script Date: 6/22/2020 1:14:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Location](
	[ID] [char](3) NOT NULL,
	[Name] [varchar](50) NULL,
 CONSTRAINT [PK_Location] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetail]    Script Date: 6/22/2020 1:14:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetail](
	[OrderID] [int] NOT NULL,
	[TourID] [char](6) NOT NULL,
	[Amount] [int] NULL,
 CONSTRAINT [PK_Order_Tour] PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC,
	[TourID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 6/22/2020 1:14:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[UserEmail] [varchar](50) NULL,
	[CreatedAt] [datetime] NULL,
	[Status] [int] NULL,
	[DiscountCode] [varchar](30) NULL,
 CONSTRAINT [PK_Order] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderStatus]    Script Date: 6/22/2020 1:14:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderStatus](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](10) NULL,
 CONSTRAINT [PK_OrderStatus] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 6/22/2020 1:14:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](20) NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Tour]    Script Date: 6/22/2020 1:14:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Tour](
	[ID] [char](6) NOT NULL,
	[Name] [varchar](50) NULL,
	[FromDate] [datetime] NULL,
	[ToDate] [datetime] NULL,
	[Departure] [char](3) NULL,
	[Destination] [char](3) NULL,
	[Price] [float] NULL,
	[Quota] [int] NULL,
	[AvailableAmount] [int] NULL,
	[Image] [varchar](100) NULL,
	[ImportedDate] [datetime] NULL,
	[Status] [int] NULL,
 CONSTRAINT [PK_Tour] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TourStatus]    Script Date: 6/22/2020 1:14:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TourStatus](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](20) NULL,
 CONSTRAINT [PK_TourStatus] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Account] ([Email], [Password], [Role], [Name]) VALUES (N'admin@gmail.com', N'123', 1, N'Le Hoang Linh')
INSERT [dbo].[Account] ([Email], [Password], [Role], [Name]) VALUES (N'long@gmail.com', N'123', 2, N'Tran Thien Long')
INSERT [dbo].[Account] ([Email], [Password], [Role], [Name]) VALUES (N'mknguyentran@yahoo.com', N'3404628746223213', 2, N'Mk Nguyễn Trần')
GO
INSERT [dbo].[DiscountCode] ([ID], [ExpiryDate], [DiscountPercentage]) VALUES (N'FLASHSALE35', CAST(N'2020-07-01T00:00:00.000' AS DateTime), 35)
INSERT [dbo].[DiscountCode] ([ID], [ExpiryDate], [DiscountPercentage]) VALUES (N'LNY2020', CAST(N'2020-03-25T00:00:00.000' AS DateTime), 35)
INSERT [dbo].[DiscountCode] ([ID], [ExpiryDate], [DiscountPercentage]) VALUES (N'SUMMER20', CAST(N'2020-07-30T00:00:00.000' AS DateTime), 20)
INSERT [dbo].[DiscountCode] ([ID], [ExpiryDate], [DiscountPercentage]) VALUES (N'VALENTINE15', CAST(N'2020-02-20T00:00:00.000' AS DateTime), 15)
GO
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'ANG', N'An Giang')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'BTR', N'Ben Tre')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'CAM', N'Ca Mau')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'DAL', N'Da Lat')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'DAN', N'Da Nang')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'HCM', N'Ho Chi Minh')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'HNO', N'Ha Noi')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'HUE', N'Hue')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'KIG', N'Kien Giang')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'MNE', N'Mui Ne')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'NTR', N'Nha Trang')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'STR', N'Soc Trang')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'TAN', N'Tay Ninh')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'VII', N'Vinh')
INSERT [dbo].[Location] ([ID], [Name]) VALUES (N'VTA', N'Vung Tau')
GO
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (4, N'ARZ001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (9, N'ARZ001', 3)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (13, N'ABC001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (13, N'ARZ001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (14, N'ABC001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (14, N'DAL001', 2)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (15, N'ABC001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (15, N'DAL001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (16, N'ABC001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (16, N'DAL001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (17, N'ARZ001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (18, N'ARZ001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (19, N'ARZ001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (20, N'ARZ001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (21, N'SBE001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (22, N'ABC001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (23, N'ABC001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (24, N'ABC001', 2)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (24, N'STR123', 3)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (25, N'SBE001', 1)
INSERT [dbo].[OrderDetail] ([OrderID], [TourID], [Amount]) VALUES (26, N'SBE001', 1)
GO
SET IDENTITY_INSERT [dbo].[Orders] ON 

INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (1, N'long@gmail.com', CAST(N'2020-06-15T14:36:43.417' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (2, N'long@gmail.com', CAST(N'2020-06-15T14:47:54.073' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (3, N'long@gmail.com', CAST(N'2020-06-15T14:57:45.073' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (4, N'long@gmail.com', CAST(N'2020-06-15T14:58:37.920' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (5, N'long@gmail.com', CAST(N'2020-06-16T13:15:10.807' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (6, N'long@gmail.com', CAST(N'2020-06-16T13:16:19.630' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (7, N'long@gmail.com', CAST(N'2020-06-16T13:21:00.833' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (8, N'long@gmail.com', CAST(N'2020-06-16T14:30:14.710' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (9, N'long@gmail.com', CAST(N'2020-06-16T15:07:24.003' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (10, N'long@gmail.com', CAST(N'2020-06-16T15:34:55.650' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (11, N'long@gmail.com', CAST(N'2020-06-18T22:33:15.853' AS DateTime), 2, N'summer20')
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (12, N'long@gmail.com', CAST(N'2020-06-18T22:55:28.743' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (13, N'long@gmail.com', CAST(N'2020-06-18T22:56:29.403' AS DateTime), 2, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (14, N'long@gmail.com', CAST(N'2020-06-18T23:22:41.747' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (15, N'long@gmail.com', CAST(N'2020-06-18T23:23:42.653' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (16, N'long@gmail.com', CAST(N'2020-06-18T23:40:20.013' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (17, N'long@gmail.com', CAST(N'2020-06-18T23:46:57.320' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (18, N'long@gmail.com', CAST(N'2020-06-18T23:47:17.647' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (19, N'long@gmail.com', CAST(N'2020-06-18T23:47:57.690' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (20, N'long@gmail.com', CAST(N'2020-06-18T23:48:44.027' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (21, N'long@gmail.com', CAST(N'2020-06-18T23:55:19.977' AS DateTime), 3, N'FLASHSALE35')
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (22, N'long@gmail.com', CAST(N'2020-06-20T12:01:47.717' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (23, N'mknguyentran@yahoo.com', CAST(N'2020-06-20T23:18:32.953' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (24, N'mknguyentran@yahoo.com', CAST(N'2020-06-20T23:32:51.247' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (25, N'mknguyentran@yahoo.com', CAST(N'2020-06-20T00:47:57.660' AS DateTime), 3, NULL)
INSERT [dbo].[Orders] ([ID], [UserEmail], [CreatedAt], [Status], [DiscountCode]) VALUES (26, N'mknguyentran@yahoo.com', CAST(N'2020-06-22T00:48:03.570' AS DateTime), 1, NULL)
SET IDENTITY_INSERT [dbo].[Orders] OFF
GO
SET IDENTITY_INSERT [dbo].[OrderStatus] ON 

INSERT [dbo].[OrderStatus] ([ID], [Name]) VALUES (1, N'pending')
INSERT [dbo].[OrderStatus] ([ID], [Name]) VALUES (2, N'completed')
INSERT [dbo].[OrderStatus] ([ID], [Name]) VALUES (3, N'cancelled')
INSERT [dbo].[OrderStatus] ([ID], [Name]) VALUES (4, N'error')
SET IDENTITY_INSERT [dbo].[OrderStatus] OFF
GO
SET IDENTITY_INSERT [dbo].[Role] ON 

INSERT [dbo].[Role] ([ID], [Name]) VALUES (1, N'admin')
INSERT [dbo].[Role] ([ID], [Name]) VALUES (2, N'user')
SET IDENTITY_INSERT [dbo].[Role] OFF
GO
INSERT [dbo].[Tour] ([ID], [Name], [FromDate], [ToDate], [Departure], [Destination], [Price], [Quota], [AvailableAmount], [Image], [ImportedDate], [Status]) VALUES (N'ABC001', N'Peaceful Da Lat', CAST(N'2020-07-08T00:00:00.000' AS DateTime), CAST(N'2020-07-15T00:00:00.000' AS DateTime), N'HCM', N'DAL', 12.99, 30, 22, N'images/ABC001.jpg', CAST(N'2020-08-06T00:00:00.000' AS DateTime), 1)
INSERT [dbo].[Tour] ([ID], [Name], [FromDate], [ToDate], [Departure], [Destination], [Price], [Quota], [AvailableAmount], [Image], [ImportedDate], [Status]) VALUES (N'ACI001', N'Ancient Capital', CAST(N'2021-02-10T00:00:00.000' AS DateTime), CAST(N'2021-02-16T00:00:00.000' AS DateTime), N'VTA', N'HNO', 59.99, 25, 25, N'images/ACI001.jpg', CAST(N'2020-09-06T00:00:00.000' AS DateTime), 1)
INSERT [dbo].[Tour] ([ID], [Name], [FromDate], [ToDate], [Departure], [Destination], [Price], [Quota], [AvailableAmount], [Image], [ImportedDate], [Status]) VALUES (N'ARZ001', N'Ocean Breeze', CAST(N'2020-06-12T00:00:00.000' AS DateTime), CAST(N'2020-06-14T00:00:00.000' AS DateTime), N'HNO', N'NTR', 12, 15, 7, N'images/ARZ001.jpg', CAST(N'2020-06-11T17:47:30.387' AS DateTime), 3)
INSERT [dbo].[Tour] ([ID], [Name], [FromDate], [ToDate], [Departure], [Destination], [Price], [Quota], [AvailableAmount], [Image], [ImportedDate], [Status]) VALUES (N'DAL001', N'Cozy Hills', CAST(N'2020-10-02T00:00:00.000' AS DateTime), CAST(N'2020-10-06T00:00:00.000' AS DateTime), N'HNO', N'DAL', 22.99, 20, 16, N'images/DAL001.jpg', CAST(N'2020-06-13T00:00:00.000' AS DateTime), 1)
INSERT [dbo].[Tour] ([ID], [Name], [FromDate], [ToDate], [Departure], [Destination], [Price], [Quota], [AvailableAmount], [Image], [ImportedDate], [Status]) VALUES (N'PPY001', N'Sandy Shore', CAST(N'2020-09-15T00:00:00.000' AS DateTime), CAST(N'2020-09-19T00:00:00.000' AS DateTime), N'BTR', N'MNE', 25.5, 25, 25, N'images/PPY001.jpg', CAST(N'2020-06-21T00:07:36.707' AS DateTime), 1)
INSERT [dbo].[Tour] ([ID], [Name], [FromDate], [ToDate], [Departure], [Destination], [Price], [Quota], [AvailableAmount], [Image], [ImportedDate], [Status]) VALUES (N'SBE001', N'Sunny Beach', CAST(N'2020-06-24T00:00:00.000' AS DateTime), CAST(N'2020-07-04T00:00:00.000' AS DateTime), N'HCM', N'NTR', 30, 30, 9, N'images/SBE001.jpg', CAST(N'2020-08-06T00:00:00.000' AS DateTime), 1)
INSERT [dbo].[Tour] ([ID], [Name], [FromDate], [ToDate], [Departure], [Destination], [Price], [Quota], [AvailableAmount], [Image], [ImportedDate], [Status]) VALUES (N'STR123', N'Wonderland', CAST(N'2020-08-02T00:00:00.000' AS DateTime), CAST(N'2020-08-05T00:00:00.000' AS DateTime), N'HUE', N'STR', 125.98999786376953, 20, 2, N'images/STR123.jpg', CAST(N'2020-06-21T22:33:47.060' AS DateTime), 1)
INSERT [dbo].[Tour] ([ID], [Name], [FromDate], [ToDate], [Departure], [Destination], [Price], [Quota], [AvailableAmount], [Image], [ImportedDate], [Status]) VALUES (N'TAN121', N'Calm Countryside', CAST(N'2020-07-29T00:00:00.000' AS DateTime), CAST(N'2020-07-31T00:00:00.000' AS DateTime), N'HCM', N'TAN', 75.5, 20, 20, N'images/TAN121.jpg', CAST(N'2020-06-21T23:41:57.153' AS DateTime), 1)
GO
SET IDENTITY_INSERT [dbo].[TourStatus] ON 

INSERT [dbo].[TourStatus] ([ID], [Name]) VALUES (1, N'available')
INSERT [dbo].[TourStatus] ([ID], [Name]) VALUES (2, N'sold out')
INSERT [dbo].[TourStatus] ([ID], [Name]) VALUES (3, N'unavailable')
SET IDENTITY_INSERT [dbo].[TourStatus] OFF
GO
ALTER TABLE [dbo].[Account]  WITH CHECK ADD  CONSTRAINT [FK_Account_Role] FOREIGN KEY([Role])
REFERENCES [dbo].[Role] ([ID])
GO
ALTER TABLE [dbo].[Account] CHECK CONSTRAINT [FK_Account_Role]
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_Order_Tour_Order] FOREIGN KEY([OrderID])
REFERENCES [dbo].[Orders] ([ID])
GO
ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_Order_Tour_Order]
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_Order_Tour_Tour] FOREIGN KEY([TourID])
REFERENCES [dbo].[Tour] ([ID])
GO
ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_Order_Tour_Tour]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Order_DiscountCode] FOREIGN KEY([DiscountCode])
REFERENCES [dbo].[DiscountCode] ([ID])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Order_DiscountCode]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Order_OrderStatus] FOREIGN KEY([Status])
REFERENCES [dbo].[OrderStatus] ([ID])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Order_OrderStatus]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Orders_Account] FOREIGN KEY([UserEmail])
REFERENCES [dbo].[Account] ([Email])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Orders_Account]
GO
ALTER TABLE [dbo].[Tour]  WITH CHECK ADD  CONSTRAINT [FK_Tour_Location] FOREIGN KEY([Departure])
REFERENCES [dbo].[Location] ([ID])
GO
ALTER TABLE [dbo].[Tour] CHECK CONSTRAINT [FK_Tour_Location]
GO
ALTER TABLE [dbo].[Tour]  WITH CHECK ADD  CONSTRAINT [FK_Tour_Location1] FOREIGN KEY([Destination])
REFERENCES [dbo].[Location] ([ID])
GO
ALTER TABLE [dbo].[Tour] CHECK CONSTRAINT [FK_Tour_Location1]
GO
ALTER TABLE [dbo].[Tour]  WITH CHECK ADD  CONSTRAINT [FK_Tour_TourStatus] FOREIGN KEY([Status])
REFERENCES [dbo].[TourStatus] ([ID])
GO
ALTER TABLE [dbo].[Tour] CHECK CONSTRAINT [FK_Tour_TourStatus]
GO
/****** Object:  StoredProcedure [dbo].[UpdateOrderStatus]    Script Date: 6/22/2020 1:14:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Procedure [dbo].[UpdateOrderStatus]
AS
	DECLARE @cancelledStatusID int
	DECLARE @completedStatusID int

	SET @cancelledStatusID = (
		Select ID
		From OrderStatus
		Where Name = 'cancelled'
	)

	SET @completedStatusID = (
		Select ID
		From OrderStatus
		Where Name = 'completed'
	)

	Update Orders 
	Set Status = @cancelledStatusID 
	Where DATEDIFF(hour, CreatedAt, CURRENT_TIMESTAMP) > 24 AND Status != @completedStatusID
GO
/****** Object:  StoredProcedure [dbo].[UpdateTourStatusBasedOnFromDate]    Script Date: 6/22/2020 1:14:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Procedure [dbo].[UpdateTourStatusBasedOnFromDate]
AS
	DECLARE @statusID int
	SET @statusID = (
		Select ID
		From TourStatus
		Where Name = 'unavailable'
	)

	Update Tour 
	Set Status = @statusID Where FromDate < CURRENT_TIMESTAMP
GO
/****** Object:  Trigger [dbo].[UpdateAvailableAmount]    Script Date: 6/22/2020 1:14:37 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Trigger [dbo].[UpdateAvailableAmount]
On [dbo].[OrderDetail]
After Insert
As
	DECLARE @tourID char(6)
	DECLARE @orderAmount int
	DECLARE @currentAmount int

	Set @tourID = (
		Select TourID
		From inserted
	)

	Set @orderAmount = (
		Select Amount
		From inserted
	)

	Set @currentAmount = (
		Select AvailableAmount
		From Tour
		Where ID = @tourID
	)

	IF (@currentAmount - @orderAmount) >= 0
	BEGIN
		Update Tour
		Set AvailableAmount = @currentAmount - @orderAmount
		Where ID = @tourID
	END
GO
ALTER TABLE [dbo].[OrderDetail] ENABLE TRIGGER [UpdateAvailableAmount]
GO
/****** Object:  Trigger [dbo].[InitiateAmount]    Script Date: 6/22/2020 1:14:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Trigger [dbo].[InitiateAmount] 
On [dbo].[Tour] 
After Insert 
As 
	DECLARE @quota int
	DECLARE @id char(6)

	Set @quota = (
		Select Quota
		From inserted
		)

	Set @id = (
		Select ID
		From inserted
		)

	Update Tour
	Set AvailableAmount = @quota 
	Where ID = @id
GO
ALTER TABLE [dbo].[Tour] ENABLE TRIGGER [InitiateAmount]
GO
USE [master]
GO
ALTER DATABASE [DreamTravelling] SET  READ_WRITE 
GO
