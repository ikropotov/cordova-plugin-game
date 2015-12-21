
module.exports = {
	_loggedin: false,
	tag: '',
	setUp: function (server_settings) {
		cordova.exec(
			function (result) {
			},
			function (error) {
			}, "Game", "setUp", [server_settings]);
	},
	login: function (tag) {
		var self = this;
		cordova.exec(function (result) {
				var playerDetail = result;
				self._loggedin = true;
				self.tag = tag;
				if (self.onLoginSucceeded)
					self.onLoginSucceeded(playerDetail);
			},
			function (error) {
				self.tag = tag;
				if (self.onLoginFailed)
					self.onLoginFailed(error);
			}, "Game", "login", []);
	},
	logout: function () {
		var self = this;
		cordova.exec(function (result) {
				self._loggedin = false;
				if (self.onLogoutSucceeded)
					self.onLogoutSucceeded();
			},
			function (error) {
				if (self.onLogoutFailed)
					self.onLogoutFailed(error);
			}, "Game", "logout", []);
	},
	isLoggedIn: function () {
		return this._loggedin;
	},
	submitScore: function (leaderboardId, score, tag) {
		var self = this;
		cordova.exec(function (result) {
				self.tag = tag;
				if (self.onSubmitScoreSucceeded)
					self.onSubmitScoreSucceeded();
			},
			function (error) {
				self.tag = tag;
				if (self.onSubmitScoreFailed)
					self.onSubmitScoreFailed();
			}, "Game", "submitScore", [leaderboardId, score]);
	},
	showLeaderboard: function (leaderboardId) {
		cordova.exec(
			function (result) {
			},
			function (error) {
			}, "Game", "showLeaderboard", [leaderboardId]);
	},
	getPlayerScore: function (leaderboardId, tag) {
		var self = this;
		cordova.exec(function (result) {
			var playerScore = result;
			self.tag = tag;
			if (self.onGetPlayerScoreSucceeded)
				self.onGetPlayerScoreSucceeded(playerScore);
		},
		function (error) {
			self.tag = tag;
			if (self.onGetPlayerScoreFailed)
				self.onGetPlayerScoreFailed();
		}, "Game", "getPlayerScore", [leaderboardId]);
	},
	unlockAchievement: function (achievementId, tag) {
		var self = this;
		cordova.exec(function (result) {
				self.tag = tag;
				if (self.onUnlockAchievementSucceeded)
					self.onUnlockAchievementSucceeded();
			},
			function (error) {
				self.tag = tag;
				if (self.onUnlockAchievementFailed)
					self.onUnlockAchievementFailed();
			}, "Game", "unlockAchievement", [achievementId]);
	},
	incrementAchievement: function (achievementId, incrementalStepOrCurrentPercent, tag) {
		var self = this;
		cordova.exec(function (result) {
				self.tag = tag;
				if (self.onIncrementAchievementSucceeded)
					self.onIncrementAchievementSucceeded();
			},
			function (error) {
				self.tag = tag;
				if (self.onIncrementAchievementFailed)
					self.onIncrementAchievementFailed();
			}, "Game", "incrementAchievement", [achievementId, incrementalStepOrCurrentPercent]);
	},
	showAchievements: function () {
		cordova.exec(
			function (result) {
			},
			function (error) {
			}, "Game", "showAchievements", []);
	},
	resetAchievements: function () {
		var self = this;
		cordova.exec(function (result) {
			if (self.onResetAchievementsSucceeded)			
				self.onResetAchievementsSucceeded();
		}, 
		function (error) {
			if (self.onResetAchievementsFailed)			
				self.onResetAchievementsFailed();
		}, "Game", "resetAchievements", []);
	},
	getPlayerImage: function () {
		var self = this;
		cordova.exec(function (result) {
			var playerImageUrl = result;
			if (self.onGetPlayerImageSucceeded)
				self.onGetPlayerImageSucceeded(playerImageUrl);
		},
		function (error) {
			if (self.onGetPlayerImageFailed)
				self.onGetPlayerImageFailed();
		}, "Game", "getPlayerImage", []);
	},
	getTokenCode: function () {
		var self = this;
		cordova.exec(function (result) {
					var userServerCode = result;
					if (self.onGetTokenCodeSucceeded)
						self.onGetTokenCodeSucceeded(userServerCode);
				},
				function (error) {
					if (self.onGetTokenCodeFailed)
						self.onGetTokenCodeFailed(error);
				}, "Game", "getTokenCode", []);
	},
	onLoginSucceeded: null,
	onLoginFailed: null,
	onLogoutSucceeded: null,
	onLogoutFailed: null,
	onGetPlayerImageSucceeded: null,
	onGetPlayerImageFailed: null,
	onGetPlayerScoreSucceeded: null,
	onGetPlayerScoreFailed: null,
	onSubmitScoreSucceeded: null,
	onSubmitScoreFailed: null,
	onUnlockAchievementSucceeded: null,
	onUnlockAchievementFailed: null,
	onIncrementAchievementSucceeded: null,
	onIncrementAchievementFailed: null,
	onResetAchievementsSucceeded: null,
	onResetAchievementsFailed: null,
	onGetTokenCodeSucceeded: null,
	onGetTokenCodeFailed: null
};
