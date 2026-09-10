local WindUI = loadstring(game:HttpGet("https://github.com/Footagesus/WindUI/releases/latest/download/main.lua"))()

local Players = game:GetService("Players")
local player = Players.LocalPlayer

local TOOL_NAME = "Kamen Rider Amazon Alpha"
local REMOTE_NAME = "Remote"

local SKILLS = {"Equip", "E", "T", "F", "G", "Dash"}

local Window = WindUI:CreateWindow({
    Title = "无CD",
    Icon = "zap",
    Folder = "NoCooldownUI",
    Size = UDim2.fromOffset(480, 400),
    Transparent = true,
    Theme = "Dark",
    Resizable = true,
    SideBarWidth = 150,
    HideSearchBar = true
})

local MainTab = Window:Tab({
    Title = "技能",
    Icon = "zap"
})

local enabled = false

local function getRemote()
    local backpack = player:FindFirstChild("Backpack")
    if backpack then
        local tool = backpack:FindFirstChild(TOOL_NAME)
        if tool then
            local remote = tool:FindFirstChild(REMOTE_NAME)
            if remote and remote:IsA("RemoteEvent") then
                return remote
            end
        end
    end
    local character = player.Character
    if character then
        local tool = character:FindFirstChild(TOOL_NAME)
        if tool then
            local remote = tool:FindFirstChild(REMOTE_NAME)
            if remote and remote:IsA("RemoteEvent") then
                return remote
            end
        end
    end
    return nil
end

local function fire(skillArg)
    local remote = getRemote()
    if remote then
        remote:FireServer(skillArg)
    end
end

MainTab:Toggle({
    Title = "无CD",
    Icon = "zap",
    Value = false,
    Callback = function(state)
        enabled = state
    end
})

MainTab:Divider()

for _, skill in ipairs(SKILLS) do
    MainTab:Button({
        Title = skill,
        Icon = "play",
        Callback = function()
            if enabled then
                fire(skill)
            end
        end
    })
end
