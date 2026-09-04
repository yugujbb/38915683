--[[
    无CD技能悬浮窗 v5.0 - 手动触发版
    适用于 Roblox 骑行时间服务器
    技能: Shokcer Army - Dash
]]

-- 防止重复加载
if game:GetService("Players").LocalPlayer.PlayerGui:FindFirstChild("NoCooldownGUI") thenif game:GetService("Players").LocalPlayer.PlayerGui:FindFirstChild("NoCooldownGUI") then
    game:GetService("Players").LocalPlayer.PlayerGui.NoCooldownGUI:Destroy()
end

-- 服务获取
local Players = game:GetService("Players")
local UserInputService = game:GetService("UserInputService")
local RunService = game:GetService("RunService")
local player = Players.LocalPlayer
local playerGui = player:WaitForChild("PlayerGui")

-- ==================== 配置 ====================
local CONFIG = {
    TOOL_NAME = "Shokcer Army",  -- 工具名称
    REMOTE_NAME = "Remote",       -- 远程事件名称
    SKILL_ARGUMENT = "Dash",      -- 技能参数
    MANUAL_TRIGGER_KEY = Enum.KeyCode.Q  -- 手动触发按键（Q键）
}
-- ==============================================

-- 创建GUI
local screenGui = Instance.new("ScreenGui")
screenGui.Name = "NoCooldownGUI"
screenGui.ResetOnSpawn = false
screenGui.ZIndexBehavior = Enum.ZIndexBehavior.Sibling
screenGui.Parent = playerGui

-- 主框架
local mainFrame = Instance.new("Frame")
mainFrame.Name = "MainFrame"
mainFrame.Size = UDim2.new(0, 220, 0, 180)
mainFrame.Position = UDim2.new(0.75, 0, 0.65, 0)
mainFrame.BackgroundColor3 = Color3.fromRGB(25, 25, 35)
mainFrame.BorderSizePixel = 0
mainFrame.Active = true
mainFrame.Draggable = true
mainFrame.Parent = screenGui

local mainCorner = Instance.new("UICorner")
mainCorner.CornerRadius = UDim.new(0, 12)
mainCorner.Parent = mainFrame

-- 标题栏
local titleBar = Instance.new("Frame")
titleBar.Size = UDim2.new(1, 0, 0, 40)
titleBar.BackgroundColor3 = Color3.fromRGB(40, 40, 55)
titleBar.BorderSizePixel = 0
titleBar.Parent = mainFrame

local titleCorner = Instance.new("UICorner")
titleCorner.CornerRadius = UDim.new(0, 12)
titleCorner.Parent = titleBar

-- 标题
local titleText = Instance.new("TextLabel")
titleText.Text = "⚡ 无CD控制"
titleText.Size = UDim2.new(0.65, 0, 1, 0)
titleText.Position = UDim2.new(0.08, 0, 0, 0)
titleText.BackgroundTransparency = 1
titleText.TextColor3 = Color3.fromRGB(255, 200, 0)
titleText.Font = Enum.Font.GothamBold
titleText.TextSize = 16
titleText.TextXAlignment = Enum.TextXAlignment.Left
titleText.Parent = titleBar

-- 最小化按钮
local minimizeButton = Instance.new("TextButton")
minimizeButton.Size = UDim2.new(0, 32, 0, 32)
minimizeButton.Position = UDim2.new(0.82, 0, 0, 4)
minimizeButton.Text = "─"
minimizeButton.BackgroundColor3 = Color3.fromRGB(60, 60, 80)
minimizeButton.TextColor3 = Color3.fromRGB(255, 255, 255)
minimizeButton.Font = Enum.Font.GothamBold
minimizeButton.TextSize = 18
minimizeButton.BorderSizePixel = 0
minimizeButton.Parent = titleBar

local minCorner = Instance.new("UICorner")
minCorner.CornerRadius = UDim.new(0, 6)
minCorner.Parent = minimizeButton

-- 无CD开关按钮
local toggleButton = Instance.new("TextButton")
toggleButton.Size = UDim2.new(0.85, 0, 0, 45)
toggleButton.Position = UDim2.new(0.075, 0, 0.28, 0)
toggleButton.Text = "无CD: 关闭"
toggleButton.BackgroundColor3 = Color3.fromRGB(180, 50, 50)
toggleButton.TextColor3 = Color3.fromRGB(255, 255, 255)
toggleButton.Font = Enum.Font.GothamBold
toggleButton.TextSize = 15
toggleButton.BorderSizePixel = 0
toggleButton.Parent = mainFrame

local toggleCorner = Instance.new("UICorner")
toggleCorner.CornerRadius = UDim.new(0, 8)
toggleCorner.Parent = toggleButton

-- 状态显示
local statusLabel = Instance.new("TextLabel")
statusLabel.Size = UDim2.new(1, 0, 0, 20)
statusLabel.Position = UDim2.new(0, 0, 0.58, 0)
statusLabel.Text = "状态: 正常模式"
statusLabel.BackgroundTransparency = 1
statusLabel.TextColor3 = Color3.fromRGB(150, 150, 150)
statusLabel.Font = Enum.Font.Gotham
statusLabel.TextSize = 12
statusLabel.Parent = mainFrame

-- 按键提示
local keyHintLabel = Instance.new("TextLabel")local keyHintLabel = Instance.new("TextLabel")
keyHintLabel.Size = UDim2.new(1, 0, 0, 20)
keyHintLabel.Position = UDim2.new(0, 0, 0.72, 0)
keyHintLabel.Text = "手动触发: 按 Q 键"
keyHintLabel.BackgroundTransparency = 1
keyHintLabel.TextColor3 = Color3.fromRGB(100, 150, 200)
keyHintLabel.Font = Enum.Font.Gotham
keyHintLabel.TextSize = 11
keyHintLabel.Parent = mainFrame

-- 悬浮球
local floatingButton = Instance.new("TextButton")local floatingButton = Instance.new("TextButton")
floatingButton.Size = UDim2.new(0, 55, 0, 55)
floatingButton.Position = UDim2.new(0.88, 0, 0.75, 0)
floatingButton.Text = "⚡"
floatingButton.BackgroundColor3 = Color3.fromRGB(40, 40, 55)
floatingButton.TextColor3 = Color3.fromRGB(255, 200, 0)
floatingButton.Font = Enum.Font.GothamBold
floatingButton.TextSize = 24
floatingButton.BorderSizePixel = 0
floatingButton.Visible = false
floatingButton.Active = true
floatingButton.Draggable = true
floatingButton.Parent = screenGui

local floatCorner = Instance.new("UICorner")
floatCorner.CornerRadius = UDim.new(0, 27)
floatCorner.Parent = floatingButton

local floatStroke = Instance.new("UIStroke")local floatStroke = Instance.new("UIStroke")
floatStroke.Color = Color3.fromRGB(255, 200, 0)
floatStroke.Thickness = 2
floatStroke.Parent = floatingButton

-- ==================== 核心功能 ====================
local isMinimized = false
local noCooldownEnabled = falselocanCdwEebe=fs

-- 获取技能的RemoteEvent
local function 
