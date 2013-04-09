Deploying to Heroku
===================

1. Install heroku toolbelt, sign up to heroku (see heroku site for details)

2. Add buildpack to the application

        $ heroku config:add BUILDPACK_URL=https://github.com/heroku/heroku-buildpack-scala.git --app your_app_name
        
    (Described here: <http://stackoverflow.com/questions/11510256/deploy-a-play-2-0-2-application-on-heroku>)

3. Add Heroku remote to your local Git repository (see git documentation for details)

    Git is used to deploy the application to heroku.
    See remote URL in heroku control panel on heroku site. 

4. Set up heroku ssh keys (key authentication is the only method supported by Heroku Git repository)

        $ heroku keys

    generates some keys in Linux (in windows it failed), but due to some reason I can't use it. Added existing key with:

        $ heroku keys:add ~/.ssh/id_rsa.pub

5.  May need to set up DATABASE_URL manually (also may need to delete default DB):

        $ heroku addons:add heroku-postgresql:dev
        $ heroku config 

    (get variable name for following step)

        $ heroku pg:promote HEROKU_POSTGRESQL_RED_URL

5. Deploy

    This test application isn't in git root, so it complicates the task.
    Script `messenger_heroku_deploy.sh` from learning project root deploys to heroku
    It uses git-subtree module. In windows distributions it's usually in place, in Linux you may need to install it.
    A good manual: <http://www.betaful.com/2011/01/i-love-git-subtree/>

        $ git clone https://github.com/apenwarr/git-subtree.git
        $ cd git-subtree    
        $ sudo ./install.sh 

    How script works: <http://stackoverflow.com/questions/7539382/how-can-i-deploy-from-a-git-subdirectory>

        $ git subtree push --prefix play/messenger heroku master

    If screwed up local history (for example, via `git commit --amend`) then use `messenger_heroku_force_deploy.sh`

6. Play with it

    See running instances:

        $ heroku ps

    See logs:

        $ heroku logs

    Set instances number:

        $ heroku ps:scale web=1

    Open application in browser:

        $ heroku open
	

