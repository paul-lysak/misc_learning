#!/bin/sh
git push heroku `git subtree split --prefix play/messenger master`:master --force
